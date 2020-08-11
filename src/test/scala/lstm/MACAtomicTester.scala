package lstm 
import chisel3._
import chisel3.iotesters
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec} 
import chisel3.stage.ChiselStage

/* testbench template for chisel designs 
 * override "MACAtomic" with your specific module name, gonna write a python script to automate that */ 

class MACAtomicUnitTester (c:MACAtomic) extends PeekPokeTester(c) {

  poke(c.io.west,   10)
  poke(c.io.north,  10)
  poke(c.io.weight, 10) 

  step(1) 

  expect(c.io.east, 10)
  expect(c.io.south, 110) 

} 




class MACAtomicTester extends ChiselFlatSpec {

  val args = Array[String]("--is-verbose","--generate-vcd-output","on","--target-dir","test/vcd/MACAtomic","--backend-name","verilator","--top-name","vcd") 

  "module" should "do something" in {
    iotesters.Driver.execute(args, () => new MACAtomic(16,16,16)) 
    {
      c => new MACAtomicUnitTester(c) 
    } should be(true) } 
} 

