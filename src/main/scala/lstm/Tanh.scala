/*
 *
 * LUT-based implementation of Tanh. This is pretty expensive
 * space-wise since we're including values for the *entire*
 * domain
 *
 * Kyle C. Hale (c) 2020 <khale@cs.iit.edu>
 * See README.md for license details.
 *
 */

package lstm

import chisel3._
import chisel3.util._
import math._

//
// Tanh: Clocked, Vector application of Tanh to
//       fixed-point values. Uses a LUT of size
//       2^w where w is datatype width.
//
// @inparam w: bit width of elements
// @inparam n: vector dimension (number of elements)
//
// @in  in: input vector
// @out out: output vector (activation)
//
class Tanh(val w: Int, val n:Int) extends Module {
  val io = IO(new Bundle {
    val in  = Input(Vec(n, SInt(w.W)))
    val out = Output(Vec(n, SInt(w.W)))
  })

  // 2^w entries. Likely either 256 or 65K. 
  // TODO: evenly space across domain and snap (piecewise
  def tanhTable = {
    // LUT setup: because the domain is signed ints, we need to offset:
    // for 8-bit ints, 
    // lut[  0] holds tanh(-128)
    // lut[127] holds tanh(0) 
    // lut[255] holds tanh(127)
    val inits = (0 until 1<<w).map(i => round(math.tanh((i - (1<<(w-1))).toDouble)).asSInt(w.W))
    VecInit(inits)
  }


  def mycounter(n: SInt) = n + (1<<(w-1)).S
  def fixedTanh(n: SInt) = tanhTable(mycounter(n).asUInt)

  val outreg = Reg(Vec(n, SInt(w.W)))

  (0 until n).foreach(i => outreg(i) := fixedTanh(io.in(i)))

  io.out := outreg
}
