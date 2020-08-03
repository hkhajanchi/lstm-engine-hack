package lstm 

import chisel3._ 


class foo extends Module { 


  val io = IO( new Bundle { 
    val enable = Input(Bool())
    val result = Output(Bool())
  }) 

  
  val seq_foo = Seq.fill(10) (true.B) 
  io.result := seq_foo.reduceLeft(_&&_) 

} 
