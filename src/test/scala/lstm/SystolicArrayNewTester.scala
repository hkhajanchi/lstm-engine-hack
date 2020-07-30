
package lstm


import chisel3._
import chisel3.iotesters
import chisel3.iotesters.{PeekPokeTester,Driver, ChiselFlatSpec}

import chisel3.stage.ChiselStage


class SystolicArrayNewUnitTester (c:SystolicArrayNew) extends PeekPokeTester(c) {

 def createRandInt(width:Int) : Int = rnd.nextInt(1<<width) - (1<<width-1) 

 val test_n = Array.fill(c.vec_len){createRandInt(16)} 
 val test_w = Array.fill(c.vec_len){createRandInt(16)} 
 
 val test_wgts = Array.ofDim[Int](c.vec_len, c.vec_len)
 

 /* Send all the weights */ 
 for (i <- 0 until c.vec_len) {
   for (j <- 0 until c.vec_len) {

     poke(c.io.weights(i)(j), createRandInt(c.bw))

     } 
   }

 step(1)

 expect(c.io.weights_are_loaded, true.B)

 step(2)
 peek(c.io.fake_output)


} 

class SystolicArrayNewTester extends ChiselFlatSpec {


  "Systolic Array Unit" should "compute SAXPY" in {

    iotesters.Driver.execute(Array[String]("--is-verbose", "--generate-vcd-output","on", "--target-dir","test/vcd/systolic_test", "vcd"), () => new SystolicArrayNew(2,2,2,16)) 
    {
      c => new SystolicArrayNewUnitTester(c) 
    } should be(true) } 
}


