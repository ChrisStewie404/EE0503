/**
 * AdderTree is a Chisel module that implements a tree-based adder.
 *
 * @param width      the bit width per input data
 * @param numInputs  the number of inputs to the adder tree
 */
package Lab2

import chisel3._
import chisel3.util._

// 定义参数化的AdderTree模块, width为每一个输入的位宽，numInputs为输入的个数
class AdderTree(width: Int, numInputs: Int) extends Module {
  require(numInputs > 1, "AdderTree must have more than one input")

  val io = IO(new Bundle {
    val inputs = Input(Vec(numInputs, UInt(width.W))) // 输入为numInputs个width位宽的无符号整数
    val output = Output(UInt((width+log2Ceil(numInputs)).W)) // 输出的位宽为输入位宽+log2(numInputs)，防止加法溢出
  }) 

    // +&表示保留进位
    io.output := io.inputs.reduce(_ +& _)
}

