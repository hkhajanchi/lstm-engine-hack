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

import java.nio.file.{Files, Paths}
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.io.File

import chisel3._
import chisel3.util._
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}
import firrtl.FileUtils


class FrontendRomUnitTester(c: FrontendRom) extends PeekPokeTester(c) {
  for (addr <- 0 until c.depth) {
    poke(c.io.addr, addr)
    step(1)
    println(s"peek from $addr:")
    for (i <- 0 until c.length) {
      println(f"  vec[$i] = ${peek(c.io.out(i))}%x")
      expect(c.io.out(i), addr * c.length + i)
    }
  }
}

class FrontendRomTester extends ChiselFlatSpec {
  private val backendNames = Array("verilator")

  val targetDirName = "test_run_dir/vec_mem_test"
  FileUtils.makeDirectory(targetDirName)

  for (i <- 0 until 4) {
    val target = Paths.get(targetDirName + "/mem" + s"$i" + ".txt")
    val src = Paths.get("/hack/mem" + s"$i" + ".txt")
    println(s"Src is $src Target is $target")
    Files.copy(src, target, REPLACE_EXISTING)
  }

  for ( backendName <- backendNames ) {
    "FrontendRom" should s"properly store vector file data(with $backendName)" in {
      Driver(() => new FrontendRom(w=8, depth=8, length=4), backendName) {
        c => new FrontendRomUnitTester(c)
      } should be (true)
    }
  }

  "Basic test using Driver.execute" should "be used as an alternative way to run specification" in {
    iotesters.Driver.execute(Array(), () => new FrontendRom(w=8, depth=8, length=4)) {
      c => new FrontendRomUnitTester(c)
    } should be (true)
  }

  if(backendNames.contains("verilator")) {
    "using --backend-name verilator" should "be an alternative way to run using verilator" in {
      iotesters.Driver.execute(Array("--backend-name", "verilator"), () => new FrontendRom(w=8, depth=8, length=4)) {
        c => new FrontendRomUnitTester(c)
      } should be(true)
    }
  }

  "running with --is-verbose" should "show more about what's going on in your tester" in {
    iotesters.Driver.execute(Array("--is-verbose"), () => new FrontendRom(w=8, depth=8, length=4)) {
      c => new FrontendRomUnitTester(c)
    } should be(true)
  }

  /**
   * By default verilator backend produces vcd file, and firrtl and treadle backends do not.
   * Following examples show you how to turn on vcd for firrtl and treadle and how to turn it off for verilator
   */

  "running with --generate-vcd-output on" should "create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on", "--target-dir", "test_run_dir/make_a_vcd", "--top-name", "make_a_vcd"),
      () => new FrontendRom(w=8, depth=8, length=4)
    ) {

      c => new FrontendRomUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_a_vcd/FrontendRom.vcd").exists should be (true)
  }

  "running with --generate-vcd-output off" should "not create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "off", "--target-dir", "test_run_dir/make_no_vcd", "--top-name", "make_no_vcd",
        "--backend-name", "verilator"),
      () => new FrontendRom(w=8, depth=8, length=4)
    ) {

      c => new FrontendRomUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_no_vcd/make_a_vcd.vcd").exists should be (false)

  }

}
