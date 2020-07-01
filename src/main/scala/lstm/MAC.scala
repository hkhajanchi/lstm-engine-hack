package lstm 

import chisel3._ 

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

    val west  = Input(SInt(a.W))
    val north = Input(SInt(b.W))
    
    val weight = Input(SInt(c.W))

    val south = Output(SInt()) 
    val east = Output(SInt()) 
    
    })


    // Latch the multiplication - available after clk 1 
    val x = RegNext(io.west * io.weight)
    val east_reg = RegNext(io.west)

    //available after clk 2 
    io.south := x + io.north //assuming 0 clk latency for add 
    io.east  := east_reg 
}

