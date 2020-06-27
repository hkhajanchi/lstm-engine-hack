
package lstm

import chisel3.iotesters
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}
import java.io.File

class ReLuUnitTester(c: ReLu) extends PeekPokeTester(c) {

  val ins  = Array.fill(c.n){0}
  val outs = Array.fill(c.n){0}

  // 10 test iterations
  for (i <- 0 until 10) {

    // initialize the test vector
    for (j <- 0 until c.n) {
      ins(j)  = rnd.nextInt(1 << c.width) - (1 << (c.width-1))
      outs(j) = if (ins(j) >= 0) ins(j) else 0
      poke(c.io.in(j), ins(j))
    }

    step(1)

    for (j <- 0 until c.n) {
      expect(c.io.out(j), outs(j))
    }
  }
}

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly lstm.ReLuTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly lstm.ReLuTester'
  * }}}
  */
class ReLuTester extends ChiselFlatSpec {
  private val backendNames = if(firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
    Array("firrtl", "verilator")
  }
  else {
    Array("firrtl")
  }
  for ( backendName <- backendNames ) {
    "ReLu" should s"calculate proper greatest common denominator (with $backendName)" in {
      Driver(() => new ReLu(8, 8), backendName) {
        c => new ReLuUnitTester(c)
      } should be (true)
    }
  }

  "Basic test using Driver.execute" should "be used as an alternative way to run specification" in {
    iotesters.Driver.execute(Array(), () => new ReLu(8,8)) {
      c => new ReLuUnitTester(c)
    } should be (true)
  }

  if(backendNames.contains("verilator")) {
    "using --backend-name verilator" should "be an alternative way to run using verilator" in {
      iotesters.Driver.execute(Array("--backend-name", "verilator"), () => new ReLu(8,8)) {
        c => new ReLuUnitTester(c)
      } should be(true)
    }
  }

  "running with --is-verbose" should "show more about what's going on in your tester" in {
    iotesters.Driver.execute(Array("--is-verbose"), () => new ReLu(8,8)) {
      c => new ReLuUnitTester(c)
    } should be(true)
  }

  /**
    * By default verilator backend produces vcd file, and firrtl and treadle backends do not.
    * Following examples show you how to turn on vcd for firrtl and treadle and how to turn it off for verilator
    */

  "running with --generate-vcd-output on" should "create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on", "--target-dir", "test_run_dir/make_a_vcd", "--top-name", "make_a_vcd"),
      () => new ReLu(8,8)
    ) {

      c => new ReLuUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_a_vcd/ReLu.vcd").exists should be (true)
  }

  "running with --generate-vcd-output off" should "not create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "off", "--target-dir", "test_run_dir/make_no_vcd", "--top-name", "make_no_vcd",
      "--backend-name", "verilator"),
      () => new ReLu(8,8)
    ) {

      c => new ReLuUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_no_vcd/make_a_vcd.vcd").exists should be (false)

  }

}



