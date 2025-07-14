package Lab2

import chisel3._
import chisel3.util._

case class vecDotFSM_Configs(
                        vecDWidth: Int = 8,
                        resDWidth: Int = 32,
                        vecLen: Int = 16,
                        vecDepth: Int = 4,
                        SEL_A: Int=0,
                        SEL_B: Int=1,
                        SEL_D: Int=2
                        )

class FSM_VecDotProductModule(cfg:vecDotFSM_Configs) extends Module
{
    val io = IO(new Bundle
    {
        // ******** Input **********
        val dataIn = Input(UInt(cfg.vecDWidth.W))
        val ld_en = Input(Bool())   // 加载
        val buf_sel =Input(UInt(log2Ceil(3).W))   // 加载哪个向量 A or B or d , 3个选项，宽度为log2Ceil(3)
        val dept_ptr =Input(UInt(log2Ceil(cfg.vecDepth).W))    // 加载由buf_sel选定的buf(A/B)中的哪个向量; 该信号同时也用于指定加载d中的哪个元素
        val elem_ptr =Input(UInt(log2Ceil(cfg.vecLen).W))     // 加载由buf_sel选定的buf(A/B)中，并由dept_ptr确定的向量中的哪个元素; 
        val start = Input(Bool())       // 加载完成后，给信号开始计算(启动状态机)
        
        // ******** Output **********
        val dataOut =Output(UInt(cfg.resDWidth.W))
        val finish=Output(Bool())  // 状态机计算完后给出结束信号表示算完了 
    })

    io.finish:=false.B

    val vec_A_groups = Seq.fill(cfg.vecDepth, cfg.vecLen)(Reg(UInt(8.W)))
    val vec_B_groups = Seq.fill(cfg.vecDepth, cfg.vecLen)(Reg(UInt(8.W)))
    val d_reg_groups = Seq.fill(cfg.vecDepth)(Reg(UInt(8.W)))
    val res_reg = RegInit((0.U(cfg.resDWidth.W)))
    val depth_cnt = RegInit(0.U(log2Ceil(cfg.vecDepth).W))

    for (depth <- 0 until cfg.vecDepth)
    {
        for (elem <- 0 until cfg.vecLen)
        {
            val can_load = io.ld_en && io.buf_sel===cfg.SEL_A.U && io.dept_ptr===depth.U && io.elem_ptr===elem.U
            vec_A_groups(depth)(elem):=Mux(
                can_load,
                io.dataIn,
                vec_A_groups(depth)(elem)
            )
        }
    }
    for (depth <- 0 until cfg.vecDepth)
    {
        for (elem <- 0 until cfg.vecLen)
        {
            val can_load = io.ld_en && io.buf_sel===cfg.SEL_B.U && io.dept_ptr===depth.U && io.elem_ptr===elem.U
            vec_B_groups(depth)(elem):=Mux(
                can_load,
                io.dataIn,
                vec_B_groups(depth)(elem)
            )
        }
    }
    for (depth <- 0 until cfg.vecDepth)
    {
        val can_load = (io.ld_en && io.buf_sel===cfg.SEL_D.U && io.dept_ptr===depth.U)
        dontTouch(can_load)
        d_reg_groups(depth):=Mux(can_load,io.dataIn,d_reg_groups(depth))
        dontTouch(can_load)
    }

    val product_in_a = WireInit(VecInit(Seq.fill(cfg.vecLen)(0.U(cfg.resDWidth.W))))
    val product_in_b = WireInit(VecInit(Seq.fill(cfg.vecLen)(0.U(cfg.resDWidth.W))))
    val productRes = WireInit(VecInit(Seq.fill(cfg.vecLen)(0.U(cfg.resDWidth.W))))

    for(depth <- 0 until cfg.vecDepth)
    {
        for(elem <- 0 until cfg.vecLen)
        {
            when(depth_cnt===depth.U)
            {
                product_in_a(elem):=vec_A_groups(depth)(elem)
                product_in_b(elem):=vec_B_groups(depth)(elem)
            }
        }
    }
    productRes.zipWithIndex.map{
        case(product_res,index) => product_res:=product_in_a(index) * product_in_b(index)
    }

    val adderTree = Module( new AdderTree(width=cfg.resDWidth,numInputs=cfg.vecLen) )
    adderTree.io.inputs:= productRes

    val start :: execute :: finish :: Nil = Enum(3)
    val state=RegInit(start)

    switch(state)
    {
        is(start)
        {
            when(io.start)
            {
                depth_cnt:=0.U
                res_reg:=0.U
                state:=execute
            }
        }
        is(execute)
        {
            for(depth <- 0 until cfg.vecDepth)
            {
                when(depth_cnt===depth.U)
                {
                    res_reg:=adderTree.io.output+d_reg_groups(depth)
                }
            }
            state:=finish
        }
        is(finish)
        {
            io.finish:=true.B
            state:=Mux(
                depth_cnt===(cfg.vecDepth-1).U,
                start,
                execute
            )
            depth_cnt:=depth_cnt+1.U
        }
    }
    io.dataOut:=res_reg
}