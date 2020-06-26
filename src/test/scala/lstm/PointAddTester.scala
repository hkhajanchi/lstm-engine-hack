package lstm

import chisel3.iotesters
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}
import java.io.File

class PointAddUnitTester(c: PointAdd) extends PeekPokeTester(c) {

  val insa = Array.fill(c.n){0}
  val insb = Array.fill(c.n){0}
  val outs = Array.fill(c.n){0}

  // 10 test iterations
  for (i <- 0 until 10) {

    // initialize the test vector
    for (j <- 0 until c.n) {
      insa(j) = rnd.nextInt((1 << c.w-2) - 1)
      insb(j) = rnd.nextInt((1 << c.w-2) - 1)
      outs(j) = insa(j) + insb(j)
      poke(c.io.ina(j), insa(j))
      poke(c.io.inb(j), insb(j))
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
  * testOnly lstm.PointAddTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly lstm.PointAddTester'
  * }}}
  */
class PointAddTester extends ChiselFlatSpec {
  private val backendNames = if(firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
    Array("firrtl", "verilator")
  }
  else {
    Array("firrtl")
  }
  for ( backendName <- backendNames ) {
    "ReLu" should s"calculate proper greatest common denominator (with $backendName)" in {
      Driver(() => new PointAdd(8,8), backendName) {
        c => new PointAddUnitTester(c)
      } should be (true)
    }
  }

  "Basic test using Driver.execute" should "be used as an alternative way to run specification" in {
    iotesters.Driver.execute(Array(), () => new PointAdd(8,8)) {
      c => new PointAddUnitTester(c)
    } should be (true)
  }

  if(backendNames.contains("verilator")) {
    "using --backend-name verilator" should "be an alternative way to run using verilator" in {
      iotesters.Driver.execute(Array("--backend-name", "verilator"), () => new PointAdd(8,8)) {
        c => new PointAddUnitTester(c)
      } should be(true)
    }
  }

  "running with --is-verbose" should "show more about what's going on in your tester" in {
    iotesters.Driver.execute(Array("--is-verbose"), () => new PointAdd(8,8)) {
      c => new PointAddUnitTester(c)
    } should be(true)
  }

  /**
    * By default verilator backend produces vcd file, and firrtl and treadle backends do not.
    * Following examples show you how to turn on vcd for firrtl and treadle and how to turn it off for verilator
    */

  "running with --generate-vcd-output on" should "create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on", "--target-dir", "test_run_dir/make_a_vcd", "--top-name", "make_a_vcd"),
      () => new PointAdd(8,8)
    ) {

      c => new PointAddUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_a_vcd/ReLu.vcd").exists should be (true)
  }

  "running with --generate-vcd-output off" should "not create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "off", "--target-dir", "test_run_dir/make_no_vcd", "--top-name", "make_no_vcd",
      "--backend-name", "verilator"),
      () => new PointAdd(8,8)
    ) {

      c => new PointAddUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_no_vcd/make_a_vcd.vcd").exists should be (false)

  }

}



