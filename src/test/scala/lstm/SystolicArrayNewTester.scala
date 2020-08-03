
package lstm


import chisel3._
import chisel3.iotesters
import chisel3.iotesters.{PeekPokeTester,Driver, ChiselFlatSpec}

import chisel3.stage.ChiselStage
import scala.util.Random

class SystolicArrayNewUnitTester (c:SystolicArrayNew) extends PeekPokeTester(c) {


/* Helper Methods */ 
 def createRandInt(width:Int) : Int = Random.nextInt(1<<width) - (1<<width-1) 


/* Test Bench variables */

 val test_n = Array.fill(c.vec_len){createRandInt(8)} 
 val test_w = Array.fill(c.vec_len){createRandInt(8)} 
 
 val test_wgts = Array.ofDim[Int](c.vec_len, c.vec_len)
 test_wgts.map ( e => e.map( _+createRandInt(8) )) // overflow issues



   /* Send all the weights */ 
   for (i <- 0 until c.vec_len) {
     for (j <- 0 until c.vec_len) {

//       poke(c.io.weights(i)(j), test_wgts(i)(j) )  
         poke(c.io.weights(i)(j), 1) 
       } 
     }

   step(1)
   
   /* Send all input vector data */ 

  for (i <- 0 until c.vec_len) {

    poke(c.io.vec_b(i), 3 ) 
    poke(c.io.vec_x(i), 3 ) 

  }

  poke(c.io.compute_enable, true) 
  step(100)  


  /* peek all the outputs */ 

  for ( i <- 0 until c.vec_len) 
  {
    peek(c.io.vec_out(i) ) 
    } 


}



class SystolicArrayNewTester extends ChiselFlatSpec {


  "Systolic Array Unit" should "compute SAXPY" in {

    iotesters.Driver.execute(Array[String]("--is-verbose", "--generate-vcd-output","on", "--target-dir","test/vcd/systolic_test", "vcd"), () => new SystolicArrayNew(2,2,2,16)) 
    {
      c => new SystolicArrayNewUnitTester(c) 
    } should be(true) } 
}


