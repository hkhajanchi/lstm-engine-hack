
package lstm

import chisel3._
import chisel3.iotesters
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}
import java.io.File
import math._

class SigmoidUnitTester(c: Sigmoid) extends PeekPokeTester(c) {

  val ins   = Array.fill(c.n){0}
  val outs  = Array.fill(c.n){0}
  val count = 10

  for (i <- 0 until count) {
    for (i <- 0 until c.n) {
      ins(i)  = rnd.nextInt(1 << c.w) - (1 << (c.w-1))
      outs(i) = round(1.0 / (1.0 + math.exp(-(ins(i).toDouble)))).toInt
      poke(c.io.in(i), ins(i))
    }

    step(1)

    for (i <- 0 until c.n) {
      expect(c.io.out(i), outs(i))
    }
  }
}

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly lstm.SigmoidTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly lstm.SigmoidTester'
  * }}}
  */
class SigmoidTester extends ChiselFlatSpec {
  private val backendNames = if(firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
    Array("firrtl", "verilator")
  }
  else {
    Array("firrtl")
  }
  for ( backendName <- backendNames ) {
    "Tanh" should s"calculate proper greatest common denominator (with $backendName)" in {
      Driver(() => new Sigmoid(8,8), backendName) {
        c => new SigmoidUnitTester(c)
      } should be (true)
    }
  }

  "Basic test using Driver.execute" should "be used as an alternative way to run specification" in {
    iotesters.Driver.execute(Array(), () => new Sigmoid(8,8)) {
      c => new SigmoidUnitTester(c)
    } should be (true)
  }

  if(backendNames.contains("verilator")) {
    "using --backend-name verilator" should "be an alternative way to run using verilator" in {
      iotesters.Driver.execute(Array("--backend-name", "verilator"), () => new Sigmoid(8,8)) {
        c => new SigmoidUnitTester(c)
      } should be(true)
    }
  }

  "running with --is-verbose" should "show more about what's going on in your tester" in {
    iotesters.Driver.execute(Array("--is-verbose"), () => new Sigmoid(8,8)) {
      c => new SigmoidUnitTester(c)
    } should be(true)
  }

  /**
    * By default verilator backend produces vcd file, and firrtl and treadle backends do not.
    * Following examples show you how to turn on vcd for firrtl and treadle and how to turn it off for verilator
    */

  "running with --generate-vcd-output on" should "create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on", "--target-dir", "test_run_dir/make_a_vcd", "--top-name", "make_a_vcd"),
      () => new Sigmoid(8,8)
    ) {

      c => new SigmoidUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_a_vcd/Tanh.vcd").exists should be (true)
  }

  "running with --generate-vcd-output off" should "not create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "off", "--target-dir", "test_run_dir/make_no_vcd", "--top-name", "make_no_vcd",
      "--backend-name", "verilator"),
      () => new Sigmoid(8,8)
    ) {

      c => new SigmoidUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_no_vcd/make_a_vcd.vcd").exists should be (false)

  }

}



