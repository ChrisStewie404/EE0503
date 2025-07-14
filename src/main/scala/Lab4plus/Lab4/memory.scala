package Lab4plus

import chisel3._
import chisel3.util._

// Memory认为是一个一维数组,可采用row major order实现矩阵的存储
class MatMem(MEM_ROW:Int,MEM_WIDTH:Int) extends Module 
{
    val io = IO(new Bundle {
        val addr = Input(UInt(log2Ceil(MEM_ROW).W))
        val writeEn = Input(Bool())
        val en = Input(Bool())
        val dataIn = Input(SInt(MEM_WIDTH.W))
        val dataOut = Output(SInt(MEM_WIDTH.W))
    })

    val mem = SyncReadMem(MEM_ROW,SInt(MEM_WIDTH.W))

    // single port 
    when(io.writeEn & io.en){
        mem.write(io.addr,io.dataIn)
    }
    io.dataOut := mem.read(io.addr, io.en&&(!io.writeEn))
}
