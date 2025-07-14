package Lab4plus

import chisel3._
import chisel3.util._
import scala.annotation.compileTimeOnly
import os.read.inputStream

class GMatMulModule(cfg:MatMulConfig) extends Module 
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
    val matA_buf=Seq.fill(cfg.mm_elem_num)(Reg(UInt(cfg.vecDWidth.W)))
    val matB_buf=Seq.fill(cfg.mm_elem_num)(Reg(SInt(cfg.vecDWidth.W)))
    val matC_buf=Seq.fill(cfg.mm_elem_num)(Reg(SInt(cfg.resDWidth.W)))
    val reg_m = RegInit(0.U(log2Ceil(cfg.gemm_matsize).W))
    val reg_k = RegInit(0.U(log2Ceil(cfg.gemm_matsize).W))
    val reg_n = RegInit(0.U(log2Ceil(cfg.gemm_matsize).W))

    val max_blk_cnt = RegInit((0.U(log2Ceil(cfg.gemm_matsize).W)))
    val max_row_cnt = RegInit((0.U(log2Ceil(cfg.gemm_matsize).W)))
    val max_col_cnt = RegInit((0.U(log2Ceil(cfg.gemm_matsize).W)))

    max_blk_cnt := ((reg_k-1.U)/cfg.matSize.U)*cfg.matSize.U
    max_row_cnt := ((reg_m-1.U)/cfg.matSize.U)*cfg.matSize.U
    max_col_cnt := ((reg_n-1.U)/cfg.matSize.U)*cfg.matSize.U

    
    val row_cnt = RegInit((0.U(log2Ceil(cfg.gemm_matsize).W))) 
    val col_cnt = RegInit((0.U(log2Ceil(cfg.gemm_matsize).W))) 
    val blk_index = RegInit((0.U(log2Ceil(cfg.gemm_matsize).W)))
    val mul_index = RegInit((0.U(log2Ceil(cfg.mm_elem_num).W)))
    val buf_sel = RegInit((0.U(log2Ceil(3).W)))

    val index = WireInit((0.U(log2Ceil(cfg.gemm_elem_num).W)))
    
    reg_m := Mux(io.reg_writeEn & io.reg_sel === cfg.SEL_M,io.reg_data,reg_m)
    reg_k := Mux(io.reg_writeEn & io.reg_sel === cfg.SEL_K,io.reg_data,reg_k)
    reg_n := Mux(io.reg_writeEn & io.reg_sel === cfg.SEL_N,io.reg_data,reg_n)

    io.busy := true.B
    io.writeEn := false.B
    io.enable := false.B
    io.dataOut := 0.S
    
    index:= MuxCase(0.U,Seq(
        (buf_sel===cfg.SEL_A) -> (row_cnt*reg_k + blk_index + ((mul_index/cfg.matSize.U)*reg_k)),
        (buf_sel===cfg.SEL_B) -> (blk_index*reg_n + col_cnt + ((mul_index/cfg.matSize.U)*reg_n)),
        (buf_sel===cfg.SEL_C) -> (row_cnt*reg_n + col_cnt + ((mul_index/cfg.matSize.U)*reg_n))
    )) +& (mul_index%cfg.matSize.U)
    io.addr:= MuxCase(0.U,Seq(
            (buf_sel===cfg.SEL_A) -> cfg.matA_baseAddr.U,
            (buf_sel===cfg.SEL_B) -> cfg.matB_baseAddr.U,
            (buf_sel===cfg.SEL_C) -> cfg.matC_baseAddr.U
    )) +& index

    for(i<- 0 until cfg.mm_elem_num)
    {
        val en_load_A = RegNext(io.enable) & i.U===RegNext(mul_index) & RegNext(buf_sel)===cfg.SEL_A
        val overflow_A =  (i.U % cfg.matSize.U + RegNext(blk_index) >= reg_k) ||
                        (i.U / cfg.matSize.U + RegNext(row_cnt) >= reg_m)
        matA_buf(i):=MuxCase(matA_buf(i),Seq(
            (en_load_A & !overflow_A) -> io.dataIn.asUInt,
            (en_load_A & overflow_A) -> 0.U
        ))
    }  
    for(i<- 0 until cfg.mm_elem_num)
    {
        val en_load_B = RegNext(io.enable) & i.U===RegNext(mul_index) & RegNext(buf_sel)===cfg.SEL_B
        val overflow_B = (i.U % cfg.matSize.U + RegNext(col_cnt) >= reg_n) ||
                        (i.U / cfg.matSize.U + RegNext(blk_index) >= reg_k)
        matB_buf(i):=MuxCase(matB_buf(i),Seq(
            (en_load_B & !overflow_B) -> io.dataIn.asSInt,
            (en_load_B & overflow_B) -> 0.S))
    }   


    val matA_row = WireInit(VecInit(Seq.fill(cfg.matSize)(0.U(cfg.vecDWidth.W))))
    val matB_col = WireInit(VecInit(Seq.fill(cfg.matSize)(0.S(cfg.vecDWidth.W))))
    for(row <- 0 until cfg.matSize)
    {
        for(col <- 0 until cfg.matSize)
        {
            val row_selected = ((mul_index/cfg.matSize.U)===row.U)
            when(row_selected)
            {
                matA_row(col):=matA_buf(row*cfg.matSize+col)
            }
        }        
    }
    for(row <- 0 until cfg.matSize)
    {
        for(col <- 0 until cfg.matSize)
        {
            val col_selected = ((mul_index%cfg.matSize.U)===col.U)
            when(col_selected)
            {
                matB_col(row):=matB_buf(row*cfg.matSize+col)
            }
        }        
    }    

    val vdp = Module(new VecDotProductEngine(cfg))
    vdp.io.vector_A_in := matA_row
    vdp.io.vector_B_in := matB_col   


    val lstart :: load :: lfinish :: cstart :: execute :: cfinish :: finish :: Nil = Enum(7)
    val state=RegInit(lstart)
    for(i <- 0 until cfg.mm_elem_num)
    {
        when(i.U===mul_index & buf_sel===cfg.SEL_C & blk_index===max_blk_cnt)
        {
            io.dataOut:=matC_buf(i)
        }
    }
 
    switch(state)
    {
        is(lstart)
        {
            when(io.start)
            {
                mul_index:=0.U
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
                mul_index===(cfg.mm_elem_num-1).U & buf_sel===cfg.SEL_B,
                cstart,
                load
            )
            mul_index:=Mux(
                mul_index===(cfg.mm_elem_num-1).U,
                0.U,
                mul_index+1.U
            )
            buf_sel:=MuxCase(buf_sel,Seq(
                (buf_sel===cfg.SEL_A & mul_index===(cfg.mm_elem_num-1).U) -> cfg.SEL_B,
                (buf_sel===cfg.SEL_B & mul_index===(cfg.mm_elem_num-1).U) -> cfg.SEL_C,
            ))
        }
        is(cstart)
        {
            mul_index:=0.U
            state:=execute
            buf_sel:=cfg.SEL_C
        }
        is(execute)
        {
            for(i <- 0 until cfg.mm_elem_num)
            {
                when(i.U===mul_index)
                {
                    matC_buf(i):=Mux(
                        blk_index===0.U,
                        vdp.io.dataOut,
                        matC_buf(i)+vdp.io.dataOut,
                    )
                }
            }      
            state:=cfinish
        }
        is(cfinish)
        {
            io.writeEn := blk_index===max_blk_cnt & 
                        !((mul_index % cfg.matSize.U + RegNext(col_cnt) >= reg_n) ||
                        (mul_index / cfg.matSize.U + RegNext(row_cnt) >= reg_m))
            io.enable := blk_index===max_blk_cnt
            state:=MuxCase(execute,Seq(
                (row_cnt===max_row_cnt &
                col_cnt===max_col_cnt &
                blk_index===max_blk_cnt &
                mul_index===(cfg.mm_elem_num-1).U) -> finish,
                (mul_index===(cfg.mm_elem_num-1).U) -> load
            ))
            mul_index:=Mux(
                mul_index===(cfg.mm_elem_num-1).U,
                0.U,
                mul_index+1.U
            )            
            blk_index:=MuxCase(blk_index,Seq(
                (mul_index===(cfg.mm_elem_num-1).U &
                blk_index===max_blk_cnt) -> 0.U,
                (mul_index===(cfg.mm_elem_num-1).U &
                blk_index=/=max_blk_cnt) -> (blk_index+cfg.matSize.U),
            ))
            col_cnt:=MuxCase(col_cnt,Seq(
                (col_cnt===max_col_cnt &
                blk_index===max_blk_cnt &
                mul_index===(cfg.mm_elem_num-1).U) -> 0.U,
                (col_cnt=/=max_col_cnt &
                blk_index===max_blk_cnt &
                mul_index===(cfg.mm_elem_num-1).U) -> (col_cnt+cfg.matSize.U),
            ))
            row_cnt:=MuxCase(row_cnt,Seq(
                (row_cnt===max_row_cnt &
                col_cnt===max_col_cnt &
                blk_index===max_blk_cnt &
                mul_index===(cfg.mm_elem_num-1).U) -> 0.U,
                (row_cnt=/=max_row_cnt &
                col_cnt===max_col_cnt &
                blk_index===max_blk_cnt &
                mul_index===(cfg.mm_elem_num-1).U) -> (row_cnt+cfg.matSize.U),
            ))
            buf_sel:=Mux(
                mul_index===(cfg.mm_elem_num-1).U,
                cfg.SEL_A,
                buf_sel
            )
        }
        is(finish)
        {
            io.busy:=false.B
            state:=lstart
        }
    }
 }



