// all code and test are under the package Lab2
// test the AdderTree module

// 8输入加法树示意图
//
//        in0     in1     in2     in3     in4     in5     in6     in7
//         |       |       |       |       |       |       |       |
//         v       v       v       v       v       v       v       v
//        +-------+-------+-------+-------+-------+-------+-------+-------+
//        |       |       |       |       |       |       |       |       |
//        |  +    |  +    |  +    |  +    |  +    |  +    |  +    |  +    |
//        |       |       |       |       |       |       |       |       |
//        +-------+-------+-------+-------+-------+-------+-------+-------+
//                 \       /       \       /       \       /       \       /
//                  \     /         \     /         \     /         \     /
//                   +---+           +---+           +---+           +---+
//                   |   |           |   |           |   |           |   |
//                   | + |           | + |           | + |           | + |
//                   |   |           |   |           |   |           |   |
//                   +---+           +---+           +---+           +---+
//                        \         /                     \         /
//                         \       /                       \       /
//                          +-----+                         +-----+
//                          |     |                         |     |
//                          |  +  |                         |  +  |
//                          |     |                         |     |
//                          +-----+                         +-----+
//                               \                         /
//                                \                       /
//                                 +---------------------+
//                                 |                     |
//                                 |         +           |
//                                 |                     |
//                                 +---------------------+
//                                                  |
//                                                  v
//                                              output

package Lab2

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class AdderTreeTest extends AnyFlatSpec with ChiselScalatestTester {

  "AdderTree test" should "pass" in {
    // 在开始进行测试前，先声明待测试加法
    val adderTree_width=16 // 每个输入为16位宽
    val adderTree_numInputs=8 //一共八个输入
    

    test(new AdderTree(width=adderTree_width,numInputs=adderTree_numInputs)) { dut =>
        // 进行10组测试
        for(testID <- 0 until 10)
        {
            
            // 数据准备 & poke
            var psum: BigInt = BigInt(0) // initialize partial sum as 0
            for(i<- 0 until adderTree_numInputs)
            {
                val rd_data=BigInt(adderTree_width, scala.util.Random)
                psum += rd_data
                dut.io.inputs(i).poke(rd_data.U) // note: input(i) denotes the i-th input
            }
            // 打印输入和期望的输出
            println(s"Begin test ${testID}: inputs are ${dut.io.inputs.map(_.peek().litValue.toString)}, sum is ${psum}")

            // 验证输出
            dut.io.output.expect(psum.U)

            // 前进一个Cycle
            dut.clock.step(1)
        }
        
      }
    }
}
