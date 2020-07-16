// See README.md for license details.
package lstm 

import chisel3._ 

class LSTM (inputVectorLength:Int, hiddenSize:Int,bw:Int) extends Module {

    val io = IO(new Bundle {
        val x_t    = Input(Vec(inputVectorLength, SInt(bw.W)))
        val h_prev = Input(Vec(hiddenSize, SInt(bw.W)))
        
        val h_t = Output(Vec(hiddenSize, SInt(bw.W)))

    })




    
}