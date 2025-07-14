package FSM_demo

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class FSMTest extends AnyFlatSpec with ChiselScalatestTester {

  "FSM demo test" should "pass" in {
    test(new FSM_counter) { dut =>

        dut.clock.step(1)

        // 启动
        val count_target=16
        dut.io.start_count.poke(true)
        dut.io.count_val.poke(count_target)
        dut.clock.step(1)

        // 等待计数完成，通过peekBoolean来查看io上的布尔值
        dut.io.start_count.poke(false)
        var cnt=0
        while(!dut.io.count_end.peekBoolean())
        {
          dut.clock.step(1)
          cnt=cnt+1
        }

        println(f"cnt=${cnt}")

      }
    }
}

