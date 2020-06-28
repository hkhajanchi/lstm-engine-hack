/*
 *
 * Vector Reverse
 *
 * Kyle C. Hale (c) 2020 <khale@cs.iit.edu>
 * See README.md for license details.
 *
 */
package lstm

import chisel3._

class VectorReverse(val w: Int, val n:Int) extends Module {
  val io = IO(new Bundle {
    val in  = Input(Vec(n, SInt(w.W)))
    val out = Output(Vec(n, SInt(w.W)))
  })

  val outreg = Reg(Vec(n, SInt(w.W)))

  (0 until n).foreach(i => outreg(i) := io.in(n-1-i))

  io.out := outreg
}
