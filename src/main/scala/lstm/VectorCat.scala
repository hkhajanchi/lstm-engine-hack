/*
 *
 * Vector Concatenation
 *
 * Kyle C. Hale (c) 2020 <khale@cs.iit.edu>
 * See README.md for license details.
 *
 */
package lstm

import chisel3._

//
// Clocked Vector Concatenation of Two Vecs. 
//
// @inparam w: bit width of elements
// @inparam na: first vector dimension (number of elements)
// @inparam nb: second vector dimension (number of elements)
//
// @in  ina: first vector
// @in  inb: second vector
// @out out: output vector (sum)
//
class VectorCat(val w: Int, val na: Int, val nb:Int) extends Module {
  val io = IO(new Bundle {
    val ina = Input(Vec(na, SInt(w.W)))
    val inb = Input(Vec(nb, SInt(w.W)))
    val out = Output(Vec(na + nb, SInt(w.W)))
  })

  val outreg = Reg(Vec(na + nb, SInt(w.W)))

  (0 until na).foreach(i => outreg(i) := io.ina(i))
  (0 until nb).foreach(i => outreg(i + na) := io.inb(i))

  io.out := outreg
}
