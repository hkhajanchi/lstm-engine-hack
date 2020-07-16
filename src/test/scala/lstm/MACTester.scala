package lstm 

import chisel3._ 
import chisel3.iotesters
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

import chisel3.experimental.DataMirror 
import chisel3.stage.ChiselStage

class MACUnitTester (c:MAC) extends PeekPokeTester(c) {

    /* Create random signed integers using Scala's nextInt() generator 
    range: -(2^(bitwitdth-1)) to 2^(bitwidth) where a,b,c are the respective operand bitwidths */

    def createRandInt(width:Int) : Int = rnd.nextInt(1<<width) - (1<<(width-1))

    println((new ChiselStage).emitVerilog(new MAC(16,16,16)))
    
    for (i <- 0 until 100 ) {

 
        val testn   = createRandInt(c.b)
        val testw   = createRandInt(c.a)
        val testwgt = createRandInt(c.c)  

        /* Expected MAC values */ 
        val out_e = testw 
        val out_s = (testw * testwgt)  + testn 

        poke(c.io.weight_input.valid, true)
        poke(c.io.weight_input.bits, testwgt)

        step(1)

        expect(c.io.weight_input.ready, false)

        poke(c.io.west.bits, testw)
        poke(c.io.north.bits, testn)
        poke(c.io.west.valid, true)
        poke(c.io.north.valid, true)
        
        step(1)

        // I've consumed my inputs
        expect(c.io.west.ready, false)
        expect(c.io.north.ready, false)

        // I've produced outputs, and they're 'valid' to be consumed
        expect(c.io.east.valid, true)
        expect(c.io.east.bits, out_e)
        expect(c.io.south.valid, true)
        expect(c.io.south.bits, out_s)
    }

}

class MACTester extends ChiselFlatSpec {

    "Mac Unit" should "multiply & accumulate input ports" in { 
    // def createrandMac : T = {
    //     val x = new MacParam(16,16,16)
    // }
    iotesters.Driver.execute(Array[String]("--is-verbose", "--generate-vcd-output","on","--target-dir","test/vcd","--top-name","vcd"),() => new MAC(16,16,16))
    {
        c => new MACUnitTester(c)
    } should be(true) }
}
