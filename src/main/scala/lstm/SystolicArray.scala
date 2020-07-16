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
    assert(rows == vec_len && cols == vec_len)

    // Wx + b, W: weight matrix, x: input vector, b: bias vector
    // Example of a SAXPY computation with fixed-point (int) numbers
    val io = IO(new Bundle {
        val vec_b   = Input(Vec(vec_len, SInt(bw.W))) // north
        val vec_x   = Input(Vec(vec_len, SInt(bw.W))) // west
        val vec_out = Output(Vec(vec_len, SInt(bw.W))) // output of the array
        val weights = Input(Vec(rows, Vec(cols, SInt(bw.W))))
    })

    val pes   = Seq.fill(rows, cols)(Module(new MAC(bw, bw, bw)))
    val pes_t = pes.transpose

    for (i <- 0 until rows) {
      for (j <- 0 until cols) {
        pes(i)(j).io.weight_input.valid := true.B
        pes(i)(j).io.weight_input.bits := io.weights(i)(j)
      }
    }

    when (pes(rows-1)(cols-1).io.weight_input.ready === false.B) {

      for (i <- 0 until cols) {
        pes(0).foreach(x => x.io.north.bits := io.vec_b(i))
        pes(0).foreach(x => x.io.north.valid := true.B)
      }

      for (i <- 0 until rows) {
        pes_t(0).foreach(x => x.io.west.bits := io.vec_x(i))
        pes_t(0).foreach(x => x.io.west.valid := true.B)
      }

      // wire up west <-> east ports
      for (i <- 0 until rows) {
        for (j <- 1 until cols) {
          pes(i)(j).io.west <> pes(i)(j - 1).io.east
        }
      }

      // wire up north <-> south ports
      for (i <- 1 until rows) {
        for (j <- 0 until cols) {
          pes(i)(j).io.north <> pes(i-1)(j).io.south
        }
      }

      for (i <- 0 until cols) { 
        when (pes(rows-1)(i).io.south.valid) {
          io.vec_out(i) := pes(rows-1)(i).io.south.bits
          pes(rows-1)(i).io.south.ready := false.B
        }
      }
    }
}
