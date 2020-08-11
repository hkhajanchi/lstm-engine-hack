
package lstm 

import chisel3._ 
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec} 
import chisel3.stage.ChiselStage




class fooUnitTester (c: foo) extends PeekPokeTester(c) {

  println( (new ChiselStage).emitVerilog(new foo) ) 
  poke(c.io.internal_0, true) 
  step(1)
  poke(c.io.internal_0, false) 
  step(3) 


  poke(c.io.external_0, true) 
  step(1)
  poke(c.io.external_1, true) 

  step(1) 
  expect(c.io.result, true) 
  expect(c.io.result_two, true) 

}



class fooTester extends ChiselFlatSpec {


  "foo" should "reduce shit" in {
  iotesters.Driver.execute(Array[String]("--is-verbose", "--generate-vcd-output", "on", "--target-dir", "test/vcd/foo","--top-name","vcd"), () => new foo ) 
  {

    c => new fooUnitTester(c) 
  }  should be(true) }

} 
