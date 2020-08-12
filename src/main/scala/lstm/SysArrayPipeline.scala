package lstm 

import chisel3._ 


/* Chisel Template for building a Hardware module 
 * to use, replace "SysArrayPipeline" with your module name and 
 * insert module/IO specific parameters as necessary 
 */

class SysArrayPipelineIO (/* IO specfic Parameters here */) extends Bundle {
}

class SysArrayPipeline (/* Module Specific Parameters here */) extends Module {
  
  /* Module Description here 
   *
   *
   */

  val io = IO(new SysArrayPipelineIO(/*IO specific params*/)) 
   
} 

