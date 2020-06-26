// See README.md for license details.

package lstm

import chisel3._

class Tanh(val width: Int, val n:Int) extends Module {
  val io = IO(new Bundle {
    val in  = Input(Vec(n, SInt(width.W)))
    val out = Output(Vec(n, SInt(width.W)))
  })

  val outreg = Reg(Vec(n, SInt(width.W)))

  (0 until n).map(i => outreg(i) := Mux(io.in(i) >= 0.S, io.in(i), 0.S))

  io.out := outreg
}
