package lstm 

import chisel3._ 
/*

Class Params 
@param rows : number of rows of MAC units in the systolic array 
@param cols : number of cols in the sys array 

@param vec_len: the length of the vectors. design assumes both input vectors are the same length
@param bw: bitwith for signed integer datatype 


*/ 
class SystolicArray (val rows:Int, val cols: Int, val vec_len : Int, val bw: Int) extends Module 
{

    val io = IO(new Bundle {
        val vec_a = Input(Vec(vec_width, SInt(bw.W))
        val vec_b = Input(Vec(vec_width, SInt(bw.W)))
    })





}