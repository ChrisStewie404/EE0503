package Lab4plus

import chisel3._
import chisel3.util._

case class MatMulConfig(
                        vecDWidth: Int = 8,     // A B的精度8b
                        resDWidth: Int = 32,    // 结果C的精度32b
                        matSize: Int = 4,       // 矩阵的大小 16*16
                        gemm_matsize: Int = 16, // GEMM的矩阵大小 32*32
                        SEL_A: UInt=0.U,        // 针对A的操作
                        SEL_B: UInt=1.U,        // 针对B的操作
                        SEL_C: UInt=2.U,        // 针对C的操作
                        OP_ACCESS_MEM: UInt=1.U,    // Top模块的操作选择-访问memory
                        OP_COMPUTE: UInt=0.U,       // Top模块的操作选择-执行计算
                        SEL_M: UInt = 0.U,
                        SEL_K: UInt = 1.U,
                        SEL_N: UInt = 2.U,
                        )
{

    val gemm_elem_num = gemm_matsize*gemm_matsize
    val mm_elem_num = matSize*matSize;
    val memory_size = gemm_elem_num*3    //memory模块需要足够大，能够容纳A B C三个矩阵，这里假设在memory中，每个矩阵元素都是占用32bit （同学们可以自行优化）
    val matA_baseAddr = 0   // 在内存中存储A矩阵的基地址 (起始地址)
    val matB_baseAddr = gemm_elem_num    // 在内存中存储B矩阵的基地址 (起始地址)
    val matC_baseAddr = 2*gemm_elem_num  // 在内存中存储C矩阵的基地址 (起始地址)
    val memory_width = resDWidth    //memory中每一个地址对应 memory_width的比特数
}

class GEMM_TOP(cfg:MatMulConfig) extends Module
{
    val io = IO(new Bundle
    {
        // ******** Load Data to Memory **********
        val dataIn = Input(SInt(cfg.memory_width.W))      //数据输入端口
        val dataOut = Output(SInt(cfg.memory_width.W))    //数据输出端口
        val addr = Input(UInt(log2Ceil(cfg.memory_size).W)) // 访存地址
        val writeEn = Input(Bool())    //写使能
        val enable = Input(Bool())    

        // operation: ld/st or exe
        val op = Input(UInt(2.W))  

        // start mm module
        val start = Input(Bool())    

        // compute finish
        val busy = Output(Bool())

        val reg_writeEn = Input(Bool())
        val reg_sel = Input(UInt(2.W))
        val reg_data = Input(UInt(cfg.vecDWidth.W))
    })

    // 实例化出的memory模块
    val memory_module=Module(new MatMem(cfg.memory_size,cfg.memory_width))
    
    // 实例化出的Matrix multiplication模块
    val mat_module=Module(new GMatMulModule(cfg))

    // ---------Connection---------
    val mat_mul_access_mem = (io.op=/=cfg.OP_COMPUTE) //如果该模块的io.op没有处在计算状态，则进行访存。
    memory_module.io.addr := Mux(mat_mul_access_mem,io.addr,mat_module.io.addr) // memory的地址io,通过选择
    memory_module.io.writeEn := Mux(mat_mul_access_mem,io.writeEn,mat_module.io.writeEn) //writeEn的控制连线
    memory_module.io.en := Mux(mat_mul_access_mem,io.enable,mat_module.io.enable)
    memory_module.io.dataIn := Mux(mat_mul_access_mem,io.dataIn,mat_module.io.dataOut) //memory的dataIn选择数据源
    mat_module.io.dataIn := memory_module.io.dataOut //矩阵乘部分（buffer）的输入为memory的输出
    io.dataOut := memory_module.io.dataOut.asSInt //为了验证最终的模块输出，我们保留了一个io.dataOut，验证计算完C buffer写回到memory里面的数值是否正确
    mat_module.io.start := io.start
    io.busy := mat_module.io.busy

    mat_module.io.reg_writeEn := io.reg_writeEn
    mat_module.io.reg_sel := io.reg_sel
    mat_module.io.reg_data := io.reg_data
}
