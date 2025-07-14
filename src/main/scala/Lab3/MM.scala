package Lab3

import chisel3._
import chisel3.util._

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
    val matA_buf=Seq.fill(cfg.mat_elem_num)(Reg(UInt(cfg.vecDWidth.W)))
    val matB_buf=Seq.fill(cfg.mat_elem_num)(Reg(SInt(cfg.vecDWidth.W)))
    val C_reg=RegInit(0.U(cfg.memory_width.W))

    val index = RegInit((0.U(log2Ceil(cfg.mat_elem_num).W))) 
    val buf_sel = RegInit((0.U(log2Ceil(3).W)))
    
    io.busy := true.B
    io.writeEn := false.B
    io.enable := false.B
    

    io.addr:= MuxCase(0.U,Seq(
            (buf_sel===cfg.SEL_A) -> cfg.matA_baseAddr.U,
            (buf_sel===cfg.SEL_B) -> cfg.matB_baseAddr.U,
            (buf_sel===cfg.SEL_C) -> cfg.matC_baseAddr.U
    )) +& index


    for(i<- 0 until cfg.mat_elem_num)
    {
        val en_load_A = RegNext(io.enable) & i.U===RegNext(index) & RegNext(buf_sel)===cfg.SEL_A
        matA_buf(i):=Mux(en_load_A,io.dataIn.asUInt,matA_buf(i))
    }  
    for(i<- 0 until cfg.mat_elem_num)
    {
        val en_load_B = RegNext(io.enable) & i.U===RegNext(index) & RegNext(buf_sel)===cfg.SEL_B
        matB_buf(i):=Mux(en_load_B,io.dataIn.asSInt,matB_buf(i))
    }   


    val matA_row = WireInit(VecInit(Seq.fill(cfg.matSize)(0.U(cfg.vecDWidth.W))))
    val matB_col = WireInit(VecInit(Seq.fill(cfg.matSize)(0.S(cfg.vecDWidth.W))))
    for(row <- 0 until cfg.matSize)
    {
        for(col <- 0 until cfg.matSize)
        {
            val row_selected = ((index/cfg.matSize.U)===row.U)
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
            val col_selected = ((index%cfg.matSize.U)===col.U)
            when(col_selected)
            {
                matB_col(row):=matB_buf(row*cfg.matSize+col)
            }
        }        
    }    
    val vdp = Module(new VecDotProductEngine(cfg))
    vdp.io.vector_A_in := matA_row
    vdp.io.vector_B_in := matB_col   

    val lstart :: load :: lfinish :: cstart :: execute :: cfinish :: Nil = Enum(6)
    val state=RegInit(lstart)
    switch(state)
    {
        is(lstart)
        {
            when(io.start)
            {
                index:=0.U
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
                index===(cfg.mat_elem_num-1).U & buf_sel===cfg.SEL_B,
                cstart,
                load
            )
            index:=Mux(
                index===(cfg.mat_elem_num-1).U,
                0.U,
                index+1.U
            )
            buf_sel:=MuxCase(buf_sel,Seq(
                (buf_sel===cfg.SEL_A & index===(cfg.mat_elem_num-1).U) -> cfg.SEL_B,
                (buf_sel===cfg.SEL_B & index===(cfg.mat_elem_num-1).U) -> cfg.SEL_C,
            ))
        }
        is(cstart)
        {
            index:=0.U
            state:=execute
            buf_sel:=cfg.SEL_C
        }
        is(execute)
        {
            io.writeEn:=true.B
            io.enable:=true.B
            state:=cfinish
        }
        is(cfinish)
        {
            io.busy:= !(index===(cfg.mat_elem_num-1).U)
            state:=Mux(
                index===(cfg.mat_elem_num-1).U,
                lstart,
                execute
            )
            index:=index+1.U
        }
    }
    // set io.busy:=false!!!
    io.dataOut:=vdp.io.dataOut

 }
