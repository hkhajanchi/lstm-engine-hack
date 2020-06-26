/*
 *
 * Vector addition
 *
 * Kyle C. Hale (c) 2020 <khale@cs.iit.edu>
 * See README.md for license details.
 *
 */
package lstm

import chisel3._

//
// PointAdd: Clocked, Point-wise addition
//            fixed-point (signed) vectors of size n
//
// @inparam w: bit width of elements
// @inparam n: vector dimension (number of elements)
//
// @in  ina: first vector
// @in  inb: second vector
// @out out: output vector (sum)
//
class PointAdd(val w: Int, val n:Int) extends Module {
  val io = IO(new Bundle {
    val ina = Input(Vec(n, SInt(w.W)))
    val inb = Input(Vec(n, SInt(w.W)))
    val out = Output(Vec(n, SInt(w.W)))
  })

  val outreg = Reg(Vec(n, SInt(w.W)))

  (0 until n).foreach(i => outreg(i) := io.ina(i) + io.inb(i))

  io.out := outreg
}
