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


  /* Initialize all registers */ 
    val busy = RegInit(false.B) 
    val comp_done = RegInit(false.B) 
    val weight_loaded = RegInit(false.B) 
   

    val weight = RegInit(0.S(c.W)) 
    val east_reg = RegInit(0.S(a.W)) 
    val north_reg = RegInit(0.S(b.W)) 
    val mac_buf = RegInit(0.S ((c*2).W)) 

    /* Input signals should be deasserted during MAC computation */ 
    io.weight_input.ready := !weight_loaded 
    io.west.ready  := !busy 
    io.north.ready := !busy

    io.east.valid  := false.B
    io.south.valid := false.B
    
    io.east.bits  := 0.S
    io.south.bits := 0.S



    // must load me up with weights first
    when (io.weight_input.valid && !weight_loaded) {
      weight := io.weight_input.bits
      weight_loaded  := true.B //will this stay asserted after weight_input.valid goes low? 
    }
    


    // Computation should only start if north/west inputs are vld,
    // and computation isnt already happening 
    // assert the internal 'busy' signal and latch in the w/n bits 
    when (io.west.valid && io.north.valid && !busy && weight_loaded) {
      busy := true.B
      
      io.west.ready := false.B
      io.north.ready := false.B

      east_reg := io.west.bits
      north_reg := io.north.bits 
    }
    
    // then I can compute
    when (busy)  {

      // Latch the multiplication - available after clk 1

      mac_buf := (east_reg * weight) + north_reg 
      
      comp_done := true.B
      busy := false.B
    }

   // Physical computation is done, now we need to assert the proper "valid" signals
   //
// this should be a continous assignment 
   io.east.bits := east_reg 
   io.south.bits := mac_buf

  when (!busy && comp_done) {
//       io.east.bits := east_reg 
 //      io.south.bits := mac_buf
       io.east.valid := true.B
       io.south.valid := true.B 
       comp_done := false.B 
  } 
 



}

