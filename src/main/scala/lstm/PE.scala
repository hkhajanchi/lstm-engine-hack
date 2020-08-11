package lstm 

import chisel3._ 


class PEIO(a:Int, b:Int, c:Int) extends AtomicMACIo(a,b,c) {
  val weight_load_enable = Input(Bool()) 
}

class PE (val a:Int, b:Int, c:Int) extends Module { 
  
  /* Module Description here 
   * Basic PE unit for Systolic Array 
   * Consists of AtomicMAC and Weight FF
   *
   */

   val io = IO(new PEIO(a,b,c)) 
   val weight_FF = RegInit(0.S(c.W)) 

   /* save weight when weight_load_enable == 1 */ 
  when (weight_load_enable) weight_FF := io.weight
  .otherwise weight_FF := weight_FF 


  val mac = Module(new AtomicMAC(a,b,c))
  
  mac.io.west   := io.west 
  mac.io.north  := io.north
  mac.io.weight := weight_FF
  io.east       := mac.io.east
  io.south      := mac.io.south
  
 



   
} 

