package lstm 

import chisel3._ 
import chisel3.iotesters
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

import chisel3.experimental.DataMirror 
import chisel3.stage.ChiselStage

class MACUnitTester (c:MAC) extends PeekPokeTester(c) {

    /* Create random signed integers using Scala's nextInt() generator 
    range: -(2^(bitwitdth-1)) to 2^(bitwidth) where a,b,c are the respective operand bitwidths */

    def createRandInt(width:Int) : Int = rnd.nextInt(1<<width) 
    
   // println((new ChiselStage).emitVerilog(new MAC(16,16,16)))
   

    val testwgt = createRandInt(8) //wgt should be static for all n&w iterations

        poke(c.io.weight_input.valid, true)
        poke(c.io.weight_input.bits, testwgt)

        step(1)
        // Set weight_valid to low
        poke(c.io.weight_input.valid, false)  
        step(1) 
        expect(c.io.weight_input.ready, false)
    
    for (i <- 0 until 100 ) {

 
        val testn   = createRandInt(8)
        val testw   = createRandInt(8)

        /* Expected MAC values */ 
        val out_e = testw 
        val out_s = (testw * testwgt)  + testn 

   // The weight is set at this point, now we can do some computation 
   //
      
        poke(c.io.west.bits, testw)
        poke(c.io.north.bits, testn)
        poke(c.io.west.valid, true)
        poke(c.io.north.valid, true)
        
        step(1)
        poke (c.io.north.valid, false)
        poke (c.io.west.valid, false) 

        step(1) 
     //   expect(c.io.north.ready, false) 
      //  expect(c.io.west.ready, false) 
// I've driven the n/w inputs, now it's time to compute the MAC 


      // I'm ready to get the outputs
     // poke(c.io.south.ready, true) 
     // poke(c.io.east.ready, true) 
      //step(1) 

        // I've produced outputs, and they're 'valid' to be consumed
        expect(c.io.east.valid, true)
        expect(c.io.east.bits, out_e)
        expect(c.io.south.valid, true)
        expect(c.io.south.bits, out_s)

        // After 1 cc, output valid signals should be deasserted 
        step(1) 

        poke(c.io.south.ready, false) 
        poke(c.io.east.ready, false) 
        
        expect(c.io.east.valid, false) 
        expect(c.io.south.valid, false) 

    }

 } 



class MACTester extends ChiselFlatSpec {

    "Mac Unit" should "multiply & accumulate input ports" in { 
    // def createrandMac : T = {
    //     val x = new MacParam(16,16,16)
    // }
    iotesters.Driver.execute(Array[String]("--is-verbose", "--generate-vcd-output","on","--target-dir","test/vcd/mac","--top-name","vcd", "--backend-name", "verilator"),() => new MAC(16,16,16))
    {
        c => new MACUnitTester(c)
    } should be(true) }
}
