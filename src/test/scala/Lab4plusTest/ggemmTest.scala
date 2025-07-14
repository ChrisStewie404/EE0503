package Lab4plus

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import scala.util.Random

class GGEMMTest extends AnyFlatSpec with ChiselScalatestTester {

  "GGeneralMatrixMultiplicationModule test" should "pass" in {
    val test_cfg=MatMulConfig()
    val m = 6
    val k = 8
    val n = 4
    test(new GEMM_TOP(cfg=test_cfg)) { dut =>
        // 进行10组测试
        dut.clock.setTimeout(100000)
        for(testID <- 0 until 10)
        {
            dut.clock.step(1)
            println(s"Begin test ${testID}")
            // data preparision & poke
            val MatA = generateRandomMatrixUnSigned(rows=m, cols=k, bits=test_cfg.vecDWidth)
            val MatB = generateRandomMatrixSigned(rows=k, cols=n,bits=test_cfg.vecDWidth)
            println("Calculate MatC.")
            val MatC = matrixMultiplyUnsignedSigned(A=MatA,B=MatB)                

            for(row <- 0 until m)
            {
                for(col <- 0 until k)
                {
                    print(MatA(row)(col))
                    print(s" ")
                }
                println()
            }
            for(row <- 0 until k)
            {
                for(col <- 0 until n)
                {
                    print(MatB(row)(col))
                    print(s" ")
                }
                println()
            }
            for(row <- 0 until m)
            {
                for(col <- 0 until n)
                {
                    print(MatC(row)(col))
                    print(s" ")
                }
                println()
            }            

            // input A to mem
            println("Loading MatA..")
            dut.io.op.poke(test_cfg.OP_ACCESS_MEM)
            for(row <- 0 until m)
            {
                println(s"Load MatA row=${row}")
                for(col <- 0 until k)
                {
                    val addr_in_mem = test_cfg.matA_baseAddr + row*k+col
                    dut.io.dataIn.poke(MatA(row)(col))
                    //println(s"MatA(${row})(${col})=${MatA(row)(col)}")
                    dut.io.addr.poke(addr_in_mem.U)
                    dut.io.writeEn.poke(true.B)
                    dut.io.enable.poke(true.B)
                    dut.clock.step(1)
                }
            }

            // input B to mem
            println("Loading MatB..")
            for(row <- 0 until k)
            {
                println(s"Load MatB row=${row}")
                for(col <- 0 until n)
                {
                    val addr_in_mem=test_cfg.matB_baseAddr+row*n+col
                    //println(s"MatB(${row})(${col})=${MatB(row)(col)}")
                    dut.io.dataIn.poke(MatB(row)(col))
                    dut.io.addr.poke(addr_in_mem.U)
                    dut.io.writeEn.poke(true.B)
                    dut.io.enable.poke(true.B)
                    dut.clock.step(1)
                }
            }

            dut.io.writeEn.poke(false.B)
            dut.io.enable.poke(false.B)

            dut.io.reg_sel.poke(test_cfg.SEL_M)
            dut.io.reg_writeEn.poke(true.B)
            dut.io.reg_data.poke(m)
            dut.clock.step(1)
            dut.io.reg_sel.poke(test_cfg.SEL_K)
            dut.io.reg_data.poke(k)
            dut.clock.step(1)
            dut.io.reg_sel.poke(test_cfg.SEL_N)
            dut.io.reg_data.poke(n)
            dut.clock.step(1)
            dut.io.reg_writeEn.poke(false.B)
            dut.io.reg_sel.poke(3.U)
            dut.io.reg_data.poke(0)
            dut.clock.step(1)

            // compute
            println("Begin Compute..")
            dut.io.op.poke(test_cfg.OP_COMPUTE)
            dut.io.start.poke(true)
            dut.clock.step(1)
            dut.io.start.poke(false)
            while(dut.io.busy.peekBoolean())
            { dut.clock.step(1)}
            println("Complete.")

            // store and check C'res
            dut.io.op.poke(test_cfg.OP_ACCESS_MEM)
            for(row <- 0 until m)
            {
                for(col <- 0 until n)
                {
                    val addr_in_mem=test_cfg.matC_baseAddr+row*n+col
                    dut.io.addr.poke(addr_in_mem.U)
                    dut.io.writeEn.poke(false.B)
                    dut.io.enable.poke(true.B)
                    dut.clock.step(1)
                    dut.io.dataOut.expect(MatC(row)(col))
                    print("ok")
                }
            }
        }
        
      }
    }

    def generateRandomMatrixSigned(rows: Int, cols: Int, bits: Int): Array[Array[Int]] = {
        val maxValue = math.pow(2, bits-1).toInt
        Array.fill(rows, cols)((Random.nextInt(maxValue) - maxValue))
    }

    def generateRandomMatrixUnSigned(rows: Int, cols: Int, bits: Int): Array[Array[Int]] = {
        val maxValue = math.pow(2, bits).toInt
        Array.fill(rows, cols)(Random.nextInt(maxValue))
    }

    def matrixMultiplyUnsignedSigned(A: Array[Array[Int]], B: Array[Array[Int]]): Array[Array[Int]] = {
        require(A(0).length == B.length, "A的列数必须等于B的行数")
        
        val result = Array.ofDim[Int](A.length, B(0).length)
        for (i <- A.indices) {
            for (j <- B(0).indices) {
                for (k <- A(0).indices) {
                    result(i)(j) += A(i)(k) * B(k)(j)
                }
            }
        }
        result
    }

}

    


