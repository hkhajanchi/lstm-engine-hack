package lstm 
import chisel3._ 
import chisel3.iotesters
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec} 
import chisel3.stage.ChiselStage


/* testbench template for chisel designs 
 * override "PE" with your specific module name, gonna write a python script to automate that */ 

class PEUnitTester (c:PE) extends PeekPokeTester(c) {

  // buffer for longer waveforms 
  step(3) 
  poke(c.io.weight_load_enable, true) 
  poke(c.io.weight, 10) 

  step(1) 
  poke(c.io.weight_load_enable, false) 
  
  // weight should be locked in now 
  step(1)
  poke(c.io.west, 10) 
  poke(c.io.north, 10) 
  expect(c.io.east, 10)
  expect(c.io.south, 110) 
  
  //poke(c.io.west,0)
  //poke(c.io.north,0)

  //outputs should be 0 now, but what happens if we change the weights?  
  poke(c.io.weight_load_enable, true) 
  poke(c.io.weight, 50) 

  step(1)
  expect(c.io.south, 510) 
  expect(c.io.east, 10) 
  
  step(5)

}



class PETester extends ChiselFlatSpec {

  val args = Array[String]("--is-verbose","--generate-vcd-output","on","--target-dir","test/vcd/PE","--backend-name","verilator","--top-name","vcd") 

  "module" should "do something" in {
    iotesters.Driver.execute(args, () => new PE(16,16,16))  
    {
      c => new PEUnitTester(c) 
    } should be(true) } 
} 

