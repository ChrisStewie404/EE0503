error id: file://<WORKSPACE>/src/main/scala/Lab4s/SGEMM.scala:scala/Int#`-`(+3).
file://<WORKSPACE>/src/main/scala/Lab4s/SGEMM.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol scala/Int#`-`(+3).
empty definition using fallback
non-local guesses:

offset: 6863
uri: file://<WORKSPACE>/src/main/scala/Lab4s/SGEMM.scala
text:
```scala
package Lab4s

import chisel3._
import chisel3.util._
import scala.annotation.compileTimeOnly
import os.read.inputStream
import Adder.Uint32_Adder

class SGMatMulModule(cfg:MatMulConfig) extends Module 
{

     val io = IO(new Bundle {
        // 访问memeory的IOs
        val addr = Output(UInt(log2Ceil(cfg.memory_size).W))
        val writeEn = Output(Bool())
        val enable = Output(Bool())
        val dataIn = Input(SInt(cfg.memory_width.W))
        val dataOut = Output(SInt(cfg.memory_width.W))

        // 开始执行信号
        val start=Input(Bool())

        // 是否执行完成信号 busy是true.B表示正在执行
        val busy=Output(Bool())

        val reg_writeEn = Input(Bool())
        val reg_sel = Input(UInt(2.W))
        val reg_data = Input(UInt(cfg.vecDWidth.W))
    })
    val loadA_buf=RegInit(0.U(cfg.gemm_matsize.W))
    val loadB_buf=RegInit(0.S(cfg.gemm_matsize.W))

    val matA_buf = Seq.fill(cfg.gemm_matsize,cfg.gemm_matsize)(Reg(UInt(cfg.vecDWidth.W)))
    val matB_buf = Seq.fill(cfg.gemm_matsize,cfg.gemm_matsize)(Reg(SInt(cfg.vecDWidth.W)))
    val matC_buf = Seq.fill(cfg.gemm_matsize,cfg.gemm_matsize)(Reg(SInt(cfg.resDWidth.W)))

    val reg_m = RegInit(0.U(log2Ceil(cfg.gemm_matsize).W))
    val reg_k = RegInit(0.U(log2Ceil(cfg.gemm_matsize).W))
    val reg_n = RegInit(0.U(log2Ceil(cfg.gemm_matsize).W))    

    val buf_sel = RegInit((0.U(log2Ceil(3).W)))
    val cross_cnt = RegInit(0.U(log2Ceil(cfg.gemm_elem_num).W))
    val load_index = RegInit(0.U(log2Ceil(cfg.gemm_elem_num).W))
    val row_cnt = RegInit((0.U(log2Ceil(cfg.gemm_elem_num).W))) 
    val col_cnt = RegInit((0.U(log2Ceil(cfg.gemm_elem_num).W))) 

    io.busy := true.B
    io.writeEn := false.B
    io.enable := false.B
    io.dataOut := 0.S

    io.addr:=MuxCase(0.U,Seq(
        (buf_sel===cfg.SEL_A) -> (cfg.matA_baseAddr.U+(load_index*cfg.gemm_matsize.U)+cross_cnt-load_index),
        (buf_sel===cfg.SEL_B) -> (cfg.matB_baseAddr.U+((cross_cnt-load_index)*cfg.gemm_matsize.U+load_index)),
        (buf_sel===cfg.SEL_C) -> (cfg.matC_baseAddr.U+row_cnt*cfg.gemm_matsize.U+col_cnt)
    ))
    for(i<- 0 until cfg.gemm_matsize)
    {
        val en_load_A = RegNext(io.enable) & i.U===RegNext(load_index) & RegNext(buf_sel)===cfg.SEL_A
        val in_range_A =(i.U < cfg.gemm_matsize.U) & 
                        (RegNext(cross_cnt) >= i.U) &
                        (RegNext(cross_cnt) < cfg.gemm_matsize.U + i.U)
        loadA_buf(i):=MuxCase(loadA_buf(i),Seq(
            (en_load_A & in_range_A) -> io.dataIn.asUInt,
            (en_load_A & !in_range_A) -> 0.U
        ))
    }
    for(i<- 0 until cfg.gemm_matsize)
    {
        val en_load_B = RegNext(io.enable) & i.U===RegNext(load_index) & RegNext(buf_sel)===cfg.SEL_B
        val in_range_B =(i.U < cfg.gemm_matsize.U) & 
                        (RegNext(cross_cnt) >= i.U) &
                        (RegNext(cross_cnt)- i.U < cfg.gemm_matsize.U)
        loadA_buf(i):=MuxCase(loadA_buf(i),Seq(
            (en_load_B & in_range_B) -> io.dataIn.asUInt,
            (en_load_B & !in_range_B) -> 0.U
        ))
    }
    val start :: load :: lfinish :: cstart :: compute :: cfinish :: write :: wfinish :: Nil = Enum(8)
    val state=RegInit(start)
    for(row <- 0 until cfg.gemm_matsize)
    {
        for(col <- 0 until cfg.gemm_matsize)
        {
            when(row.U===row_cnt & col.U===col_cnt & buf_sel===cfg.SEL_C &
            row.U < cfg.gemm_matsize.U & col.U < cfg.gemm_matsize.U){
                io.dataOut:=matC_buf(row)(col)
            }
        }
    }
    switch(state)
    {
        is(start)
        {
            when(io.start)
            {
                cross_cnt:=0.U
                load_index:=0.U
                row_cnt:=0.U
                col_cnt:=0.U
                buf_sel:=cfg.SEL_A
                state:=load
            }
        }
        is(load)
        {
            io.enable:=true.B
            state:=lfinish
        }
        is(lfinish)
        {
            state:=Mux(
                load_index===(cfg.gemm_matsize-1).U & buf_sel===cfg.SEL_B,
                cstart,
                load
            )
            load_index:=Mux(
                load_index===(cfg.gemm_matsize-1).U,
                0.U,
                load_index+1.U
            )
            buf_sel:=MuxCase(buf_sel,Seq(
                (buf_sel===cfg.SEL_A & load_index===(cfg.gemm_matsize-1).U) -> cfg.SEL_B,
                (buf_sel===cfg.SEL_B & load_index===(cfg.gemm_matsize-1).U) -> cfg.SEL_A,
            ))
        }
        is(cstart)
        {
            row_cnt:=0.U
            col_cnt:=0.U
            state:=compute
            for(row <- 0 until cfg.gemm_matsize)
            {
                for(col <- 0 until cfg.gemm_matsize)
                {
                    matA_buf(row)(col) := 0.U
                    matB_buf(row)(col) := 0.S
                    matC_buf(row)(col) := 0.S
                }
            }
        }
        is(compute)
        {
            for(row <- 0 until cfg.gemm_matsize)
            {
                for(col <- 0 until cfg.gemm_matsize)
                {
                    matC_buf(row)(col):=matC_buf(row)(col)+matA_buf(row)(col)*matB_buf(row)(col)
                }
            }
            for(row <- 0 until cfg.gemm_matsize)
            {
                for(col <- 0 until cfg.gemm_matsize)
                {
                    matA_buf(row)(col):=Mux(
                        col.U===0.U,
                        loadA_buf(row),
                        matA_buf(row)(col-1)
                    )
                }
            }
            for(row <- 0 until cfg.gemm_matsize)
            {
                for(col <- 0 until cfg.gemm_matsize)
                {
                    matB_buf(row)(col):=Mux(
                        row.U===0.U,
                        loadB_buf(col),
                        matA_buf(row-1)(col)
                    )
                }
            }   
            state:=cfinish         
        }
        is(cfinish)
        {
            val coverflow = (cross_cnt + 2.U > (2*cfg.gemm_matsize).U)
            cross_cnt := Mux(
                coverflow,
                0.U,
                cross_cnt+1.U
            )
            state:=Mux(
                coverflow,
                write,
                load
            )
            buf_sel:=Mux(
                coverflow,
                cfg.SEL_C,
                cfg.SEL_A
            )
        }
        is(write)
        {
            io.writeEn := (RegNext(row_cnt) < cfg.gemm_matsize.U) &
                          (RegNext(col_cnt) < cfg.gemm_matsize.U)
            io.enable := (RegNext(row_cnt) < cfg.gemm_matsize.U) &
                         (RegNext(col_cnt) < cfg.gemm_matsize.U)
            state := wfinish
        }
        is(wfinish)
        {
            row_cnt := Mux(
                row_cnt===(cfg.gemm_matsize-@@1).U,
                0.U,
                row_cnt+1.U
            )
            col_cnt := MuxCase(col_cnt,Seq(
                
            ))
        }
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 