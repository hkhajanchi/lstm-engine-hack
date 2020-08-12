package lstm 
import chisel3._ 
import chisel3.iotesters
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec} 
import chisel3.stage.ChiselStage


/* testbench template for chisel designs 
 * override "SysArrayPipeline" with your specific module name, gonna write a python script to automate that */ 

class SysArrayPipelineUnitTester (c:SysArrayPipeline) extends PeekPokeTester(c) {
 /* testbench logic here */ 
} 


class SysArrayPipelineTester extends ChiselFlatSpec {

  val args = Array[String]("--is-verbose","--generate-vcd-output","on","--target-dir","test/vcd/SysArrayPipeline","--backend-name","verilator","--top-name","vcd") 

  "module" should "do something" in {
    iotesters.Driver.execute(args, () => new SysArrayPipeline(/* module params here */)) 
    {
      c => new SysArrayPipelineUnitTester(c) 
    } should be(true) } 
} 

