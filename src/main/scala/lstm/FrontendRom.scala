/*
 *
 * Vector ROM that fakes the output of the STFT from
 * the frontend. Backed by a binary file.
 * 
 * Kyle C. Hale (c) 2020 <khale@cs.iit.edu>
 * See README.md for license details.
 *
 * Based on: https://github.com/freechipsproject/chisel3/wiki/Chisel-Memories
 *
 */

package lstm


import chisel3._
import chisel3.util.experimental.loadMemoryFromFile
import chisel3.util.log2Ceil


// For our current model under development, we would instantiate as:
//     Module(new FrontendRom(8, 676, 129)
//     For 676 time points, each of which is a 129-point vector of frequency
//     amplitudes from the STFT

// w is size of an element
// depth is how many vectors
// length is the dimensionality of each vector
// This memory is actually [length] distinct memories
class FrontendRom(val w: Int, val depth:Int, val length:Int) extends Module {
  val io = IO(new Bundle {
    val addr = Input(UInt(log2Ceil(depth).W))
    val out  = Output(Vec(length, SInt(w.W))) // stft time point
  })

  val mems = Array.fill(length)(Mem(depth, SInt(w.W)))

  for (i <- 0 until length) {
    loadMemoryFromFile(mems(i), s"test_run_dir/vec_mem_test/mem$i.txt")
  }

  (0 until length).foreach(i => io.out(i) := mems(i)(io.addr))
}

