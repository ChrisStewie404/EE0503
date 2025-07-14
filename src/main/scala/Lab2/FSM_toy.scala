package FSM_demo

import chisel3._
import chisel3.util._

class FSM_counter extends Module
{
    val io = IO(new Bundle
    {
        // input 开始信号和计数值
        val start_count = Input(Bool())
        val count_val = Input(UInt(log2Ceil(128).W))
        // output 计数完成输出true
        val count_end = Output(Bool())
    })

    // 输出的默认值
    io.count_end := false.B

    // 设置状态 有三个状态
    val start :: execute :: end :: Nil = Enum(3)
    // 状态寄存器，并设置初始值（即初始状态）
    val state = RegInit(start)

    // 计数用的寄存器
    val counter = RegInit(0.U(log2Ceil(128).W))

    switch(state)
    {
        is(start)
        {
            when(io.start_count)
            {
                // 保存计数值
                counter := io.count_val - 1.U
                
                // 跳转到执行状态
                state := execute
            }
        }
        is(execute)
        {
            counter := counter-1.U
            // 触发条件时跳转到结束
            // 不触发的话就每个周期保持原有状态,执行counter:=counter-1.U
            when(counter===0.U)
            {state := end}
        }
        is(end)
        {
            // 发出完成信号
            io.count_end:=true.B
            // 回到初始状态
            state:=start
        }
    }

}
