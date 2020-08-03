
package lstm 

import chisel3._ 
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec} 




class fooUnitTester (c: foo) extends PeekPokeTester(c) {




  val test_enable = true 

  poke(c.io.enable, test_enable) 
  step(1) 
  expect(c.io.result, true) 


}



class fooTester extends ChiselFlatSpec {


  "foo" should "reduce shit" in {
  iotesters.Driver.execute(Array[String]("--is-verbose", "--generate-vcd-output", "on", "--target-dir", "test/vcd/foo","--top-name","vcd"), () => new foo ) 
  {

    c => new fooUnitTester(c) 
  }  should be(true) }

} 
