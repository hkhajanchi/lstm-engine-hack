package lstm

import chisel3.iotesters
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}
import java.io.File

class VectorCatUnitTester(c: VectorCat) extends PeekPokeTester(c) {

  val insa = Array.fill(c.na){0}
  val insb = Array.fill(c.nb){0}
  val outs = Array.fill(c.na + c.nb){0}

  // 10 test iterations
  for (i <- 0 until 10) {

    // initialize the test vectors
    for (i <- 0 until c.na) {
      insa(i) = rnd.nextInt((1 << c.w-2) - 1)
      poke(c.io.ina(i), insa(i)) 
    }
    for (i <- 0 until c.nb) {
      insb(i) = rnd.nextInt((1 << c.w-2) - 1)
      poke(c.io.inb(i), insb(i))
    }

    (0 until c.na).foreach( i => outs(i) = insa(i) )
    (0 until c.nb).foreach( i => outs(i + c.na) = insb(i) )

    step(1)

    for (i <- 0 until c.na) {
      expect(c.io.out(i), outs(i))
    }
    for (i <- 0 until c.nb) {
      expect(c.io.out(i + c.na), outs(i + c.na))
    }

  }
}

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly lstm.VectorCatTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly lstm.VectorCatTester'
  * }}}
  */
class VectorCatTester extends ChiselFlatSpec {
  private val backendNames = if(firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
    Array("firrtl", "verilator")
  }
  else {
    Array("firrtl")
  }
  for ( backendName <- backendNames ) {
    "ReLu" should s"calculate proper greatest common denominator (with $backendName)" in {
      Driver(() => new VectorCat(8,8,8), backendName) {
        c => new VectorCatUnitTester(c)
      } should be (true)
    }
  }

  "Basic test using Driver.execute" should "be used as an alternative way to run specification" in {
    iotesters.Driver.execute(Array(), () => new VectorCat(8,8,8)) {
      c => new VectorCatUnitTester(c)
    } should be (true)
  }

  if(backendNames.contains("verilator")) {
    "using --backend-name verilator" should "be an alternative way to run using verilator" in {
      iotesters.Driver.execute(Array("--backend-name", "verilator"), () => new VectorCat(8,8,8)) {
        c => new VectorCatUnitTester(c)
      } should be(true)
    }
  }

  "running with --is-verbose" should "show more about what's going on in your tester" in {
    iotesters.Driver.execute(Array("--is-verbose"), () => new VectorCat(8,8,8)) {
      c => new VectorCatUnitTester(c)
    } should be(true)
  }

  /**
    * By default verilator backend produces vcd file, and firrtl and treadle backends do not.
    * Following examples show you how to turn on vcd for firrtl and treadle and how to turn it off for verilator
    */

  "running with --generate-vcd-output on" should "create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on", "--target-dir", "test_run_dir/make_a_vcd", "--top-name", "make_a_vcd"),
      () => new VectorCat(8,8,8)
    ) {

      c => new VectorCatUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_a_vcd/ReLu.vcd").exists should be (true)
  }

  "running with --generate-vcd-output off" should "not create a vcd file from your test" in {
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "off", "--target-dir", "test_run_dir/make_no_vcd", "--top-name", "make_no_vcd",
      "--backend-name", "verilator"),
      () => new VectorCat(8,8,8)
    ) {

      c => new VectorCatUnitTester(c)
    } should be(true)

    new File("test_run_dir/make_no_vcd/make_a_vcd.vcd").exists should be (false)

  }

}



