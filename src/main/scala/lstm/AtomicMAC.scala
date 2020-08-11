package lstm 

import chisel3._ 

class AtomicMACIo (val a:Int, b:Int, c:Int) extends Bundle {
  val west   = Input(SInt(a.W))
  val north  = Input(SInt(b.W))
  val weight = Input(SInt(c.W))

  val east   = Output(SInt(c.W))
  val south  = Output(SInt(c.W))
} 

class AtomicMAC (val bw_0:Int, val bw_1:Int, val bw_2:Int) extends Module {

    /* A baseline implementation of a combinational MAC unit 
     * @param bw_0 <Int> : bitwidth of operand x 
     * @param bw_1 <Int> : bitwidth of operand b 
     * @param bw_2 <Int> : bitwidth of operand w 
     *
     * Description: computes W*x + b
     */ 

    
    /* IO instantiation here */ 
    val io = IO(new AtomicMACIo(bw_0, bw_1, bw_2)) 
    
    /* Class methods */ 
   def compute (w:SInt, x:SInt, b:SInt) : SInt = w*x + b 


   io.east := io.west
   io.south := compute(io.weight, io.east, io.north) 


}
