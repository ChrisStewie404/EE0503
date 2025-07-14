package Lab3

import chisel3._
import chisel3.util._


class VecDotProductEngine(cfg:MatMulConfig) extends Module
{
    val io = IO(new Bundle
    {
        // ******** Input **********
        val vector_A_in=Vec(cfg.matSize,Input(UInt(cfg.resDWidth.W)))
        val vector_B_in=Vec(cfg.matSize,Input(SInt(cfg.resDWidth.W)))
        
        // ******** Output **********
        val dataOut =Output(SInt(cfg.resDWidth.W))
    })

   
    // 乘
    val productRes=WireInit(VecInit(Seq.fill(cfg.matSize)(0.S(cfg.resDWidth.W))))
    productRes.zipWithIndex.map{case(product_res,index)=>
        product_res:=io.vector_A_in(index).asSInt*io.vector_B_in(index)
    }

    // 加
    io.dataOut:= productRes.reduce(_ + _)
}

    


