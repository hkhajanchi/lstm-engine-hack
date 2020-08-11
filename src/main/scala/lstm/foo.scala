package lstm 

import chisel3._ 


class foo extends Module { 


  val io = IO( new Bundle { 
    val external_0 = Input(Bool()) //external conditionals 
    val external_1 = Input(Bool())
    val internal_0 = Input(Bool()) //internal conditional signal 


    val result = Output(Bool())
    val result_two = Output(Bool()) 
  }) 

 /* going to use this to test my conditional hardware design method */ 
 
   val reg_0 = RegInit(false.B) 

   when (io.external_0 && io.external_1){
     reg_0 := true.B 
   } 
   .otherwise {reg_0 := false.B}

   val reg_1 = RegNext(io.external_0 && io.external_1) 

  io.result := reg_0 
  io.result_two := reg_1  
   
} 
