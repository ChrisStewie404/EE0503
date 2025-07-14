/**
 * This file contains the definition of the VecDotProductModule class, which implements a vector dot product module.
 * The module takes two input vectors, A and B, and calculates their dot product by multiplying corresponding elements
 * and summing the results. The module supports configurable vector and result data widths, vector length, and selection
 * between vectors A and B. It also provides an enable signal for loading data into the vectors.
 *
 * @param cfg The configuration parameters for the module.
 */

package Lab2

import chisel3._
import chisel3.util._

// Define a case class to hold the module configuration parameters
case class ModuleConfigs(
                        vecDWidth: Int = 8, // vector data bit width
                        resDWidth: Int = 32, // result data bit width
                        vecLen: Int = 16, // vector length
                        SEL_A:Bool=true.B, // select vector A
                        SEL_B:Bool=false.B // select vector B
                        )

class VecDotProductModule(cfg:ModuleConfigs) extends Module  // take the ModuleConfigs as a parameter
{
    // define IO port
    val io = IO(new Bundle
    {
        val dataIn = Input(UInt(cfg.vecDWidth.W)) // data input wire
        val ld_en = Input(Bool())  // load enable wire
        val buf_sel =Input(Bool())  // buffer select wire
        val dataIn_ptr =Input(UInt(log2Ceil(cfg.vecLen).W)) // data input pointer wire (address)
        
        val dataOut =Output(UInt(cfg.resDWidth.W)) 
    })

    // 向量A的寄存器组, A_buffer里面的初始化值是0
    val vec_A = RegInit(VecInit(Seq.fill(cfg.vecLen)(0.U(cfg.vecDWidth.W))))

    // 向量B的寄存器组, B_buffer里面的初始化值是0
    val vec_B = RegInit(VecInit(Seq.fill(cfg.vecLen)(0.U(cfg.vecDWidth.W))))

    // 加载向量A, 对vec_A的每一个元素进行赋值
    for(i <- 0 until cfg.vecLen)
    {   
        vec_A(i) := Mux( 
                        io.ld_en && io.buf_sel===cfg.SEL_A && io.dataIn_ptr===i.U,  //检查是否ld_en, buf_sel是否选择A，dataIn_ptr是否等于i
                        io.dataIn, // 如果是，把dataIn的值赋给vec_A(i)
                        vec_A(i)   // 如果不是，vec_A(i)的值不变
                        )   
    }

    // 加载向量B
    for(i <- 0 until cfg.vecLen)
    {   vec_B(i) := Mux(
                        io.ld_en && io.buf_sel===cfg.SEL_B && io.dataIn_ptr===i.U,
                        io.dataIn,
                        vec_B(i)
                    )    
    }

    // 先定义乘法器输出的线
    val productRes = WireInit(VecInit(Seq.fill(cfg.vecLen)(0.U(cfg.resDWidth.W)))) // 初始化输出为一个长度为cfg.vecLen的0向量

    // 再通过描述输出和输入的计算方式，形成乘法器的定义。这里使用zipWithIndex方法遍历输入Vec，并在处理每个元素时获取其索引
    productRes.zipWithIndex.map{
        //  case下遍历productRes的每一个元素product_res，index是索引
        case(product_res,index) => product_res := vec_A(index) * vec_B(index)
    }

    // 使用AdderTree模块对叉乘结果进行求和，即规约操作
    val adderTree = Module( new AdderTree(width=cfg.resDWidth,numInputs=cfg.vecLen) )
    adderTree.io.inputs := productRes

    // 完成计算，输出结果
    io.dataOut := adderTree.io.output
}
