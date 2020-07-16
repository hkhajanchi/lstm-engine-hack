package lstm 

import chisel3._ 
import chisel3.util.Decoupled

/*
A Simple Multiply-Accumulate Unit (MAC) parameterized by operand bitwidths "a" and "b"

Returns a signed integer of bitwidth "a"+"b"

Class Parameters: 
    @a : bitwidth for west port
    @b : bitwidth for north port 
    @c:  bitwidth for weight
*/

class MAC(val a:Int, val b:Int, val c:Int) extends Module {
    
    val io = IO( new Bundle{
      val west   = Flipped(Decoupled(SInt(a.W)))
      val north  = Flipped(Decoupled(SInt(b.W)))
      val south  = Decoupled(SInt())
      val east   = Decoupled(SInt())
      val weight_input = Flipped(Decoupled(SInt(c.W)))
    })

    io.weight_input.ready := true.B

    val weight = RegInit(0.S(c.W))

    io.west.ready  := true.B
    io.north.ready := true.B

    io.east.valid  := false.B
    io.south.valid := false.B

    io.east.bits  := 0.S
    io.south.bits := 0.S

    // must load me up with weights first
    when (io.weight_input.valid) {
      weight := io.weight_input.bits
      io.weight_input.ready := false.B
    }

    // then I can compute
    when (io.west.valid && io.north.valid) {

      // Latch the multiplication - available after clk 1 
      val x        = RegNext(io.west.bits * weight)
      val east_reg = RegNext(io.west.bits)

      io.south.bits := x + io.north.bits //assuming 0 clk latency for add 
      io.east.bits  := east_reg 

      io.south.valid := true.B
      io.east.valid  := true.B

      io.west.ready  := false.B
      io.north.ready := false.B
    }
}

