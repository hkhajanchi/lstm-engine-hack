package lstm


import chisel3._
import chisel3.iotesters
import chisel3.iotesters.{PeekPokeTester,Driver, ChiselFlatSpec}

import chisel3.stage.ChiselStage


class SystolicArrayUnitTester (c:SystolicArray) extends PeekPokeTester(c) {

 def createRandInt(width:Int) : Int = rnd.nextInt(1<<width) - (1<<width-1) 

 val test_n = Array.fill(c.vec_len){createRandInt(16)} 
 val test_w = Array.fill(c.vec_len){createRandInt(16)} 
 
 val test_wgts = Array.ofDim[Int](c.vec_len, c.vec_len) 
 
 //Populate test_wgts array with random ints 
 for (i <- 0 until test_wgts.length) {
   test_wgts(i) = Array.fill(test_wgts(i).length){createRandInt(16)}
 }

 //Peek Poke and Whatever else to get the sim running
 for (i <- 0 until c.vec_len) 
 {
   poke(c.io.vec_b(i), test_n(i))
   poke(c.io.vec_x(i), test_w(i))
 } 

 for (i <- 0 until c.vec_len) 
 { 
   for (j <- 0 until c.vec_len) {
   poke(c.io.weights(i)(j), test_wgts(i)(j)) 
   }
 }


}
class SystolicArrayTester extends ChiselFlatSpec {


  "Systolic Array Unit" should "compute SAXPY" in {

    iotesters.Driver.execute(Array[String]("--is-verbose", "--generate-vcd-output","on", "--target-dir","test/vcd", "vcd"), () => new SystolicArray(2,2,2,16)) 
    {
      c => new SystolicArrayUnitTester(c) 
    } should be(true) } 
}

