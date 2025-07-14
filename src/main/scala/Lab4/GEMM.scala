package Lab4

import chisel3._
import chisel3.util._
import scala.annotation.compileTimeOnly

class MatMulModule(cfg:MatMulConfig) extends Module 
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
    })
    val matA_buf=Seq.fill(cfg.mm_elem_num)(Reg(UInt(cfg.vecDWidth.W)))
    val matB_buf=Seq.fill(cfg.mm_elem_num)(Reg(SInt(cfg.vecDWidth.W)))
    val matC_buf=Seq.fill(cfg.mm_elem_num)(Reg(SInt(cfg.resDWidth.W)))

    
    val row_cnt = RegInit((0.U(log2Ceil(cfg.gemm_matsize).W))) 
    val col_cnt = RegInit((0.U(log2Ceil(cfg.gemm_matsize).W))) 
    val blk_index = RegInit((0.U(log2Ceil(cfg.gemm_matsize).W)))
    val mul_index = RegInit((0.U(log2Ceil(cfg.mm_elem_num).W)))
    val buf_sel = RegInit((0.U(log2Ceil(3).W)))

    val index = WireInit((0.U(log2Ceil(cfg.gemm_elem_num).W)))
    
    io.busy := true.B
    io.writeEn := false.B
    io.enable := false.B
    io.dataOut := 0.S
    
    index:= MuxCase(0.U,Seq(
        (buf_sel===cfg.SEL_A) -> (row_cnt*cfg.gemm_matsize.U + blk_index),
        (buf_sel===cfg.SEL_B) -> (blk_index*cfg.gemm_matsize.U + col_cnt),
        (buf_sel===cfg.SEL_C) -> (row_cnt*cfg.gemm_matsize.U + col_cnt)
    )) + ((mul_index%cfg.matSize.U) + ((mul_index>>log2Ceil(cfg.matSize))<<log2Ceil(cfg.gemm_matsize)))
    io.addr:= MuxCase(0.U,Seq(
            (buf_sel===cfg.SEL_A) -> cfg.matA_baseAddr.U,
            (buf_sel===cfg.SEL_B) -> cfg.matB_baseAddr.U,
            (buf_sel===cfg.SEL_C) -> cfg.matC_baseAddr.U
    )) +& index

    for(i<- 0 until cfg.mm_elem_num)
    {
        val en_load_A = RegNext(io.enable) & i.U===RegNext(mul_index) & RegNext(buf_sel)===cfg.SEL_A
        matA_buf(i):=Mux(en_load_A,io.dataIn.asUInt,matA_buf(i))
    }  
    for(i<- 0 until cfg.mm_elem_num)
    {
        val en_load_B = RegNext(io.enable) & i.U===RegNext(mul_index) & RegNext(buf_sel)===cfg.SEL_B
        matB_buf(i):=Mux(en_load_B,io.dataIn.asSInt,matB_buf(i))
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
        when(i.U===mul_index & buf_sel===cfg.SEL_C & blk_index===(cfg.gemm_matsize-cfg.matSize).U)
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
            io.writeEn := blk_index===(cfg.gemm_matsize-cfg.matSize).U
            io.enable := blk_index===(cfg.gemm_matsize-cfg.matSize).U
            state:=MuxCase(execute,Seq(
                (row_cnt===(cfg.gemm_matsize-cfg.matSize).U &
                col_cnt===(cfg.gemm_matsize-cfg.matSize).U &
                blk_index===(cfg.gemm_matsize-cfg.matSize).U &
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
                blk_index===(cfg.gemm_matsize-cfg.matSize).U) -> 0.U,
                (mul_index===(cfg.mm_elem_num-1).U &
                blk_index=/=(cfg.gemm_matsize-cfg.matSize).U) -> (blk_index+cfg.matSize.U),
            ))
            col_cnt:=MuxCase(col_cnt,Seq(
                (col_cnt===(cfg.gemm_matsize-cfg.matSize).U &
                blk_index===(cfg.gemm_matsize-cfg.matSize).U &
                mul_index===(cfg.mm_elem_num-1).U) -> 0.U,
                (col_cnt=/=(cfg.gemm_matsize-cfg.matSize).U &
                blk_index===(cfg.gemm_matsize-cfg.matSize).U &
                mul_index===(cfg.mm_elem_num-1).U) -> (col_cnt+cfg.matSize.U),
            ))
            row_cnt:=MuxCase(row_cnt,Seq(
                (row_cnt===(cfg.gemm_matsize-cfg.matSize).U &
                col_cnt===(cfg.gemm_matsize-cfg.matSize).U &
                blk_index===(cfg.gemm_matsize-cfg.matSize).U &
                mul_index===(cfg.mm_elem_num-1).U) -> 0.U,
                (row_cnt=/=(cfg.gemm_matsize-cfg.matSize).U &
                col_cnt===(cfg.gemm_matsize-cfg.matSize).U &
                blk_index===(cfg.gemm_matsize-cfg.matSize).U &
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



