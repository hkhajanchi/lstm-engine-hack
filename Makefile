BUILDER:=sbt
WAVE:=gtkwave
PYTHON:=python3

test: test-pointmult \
	  test-pointadd \
	  test-relu \
	  test-tanh \
	  test-sigmoid \
	  test-fifo \
	  test-vectorcat \
	  test-vectorreverse

test-vectorcat:
	$(BUILDER) 'testOnly lstm.VectorCatTester'

test-vectorreverse:
	$(BUILDER) 'testOnly lstm.VectorReverseTester'

test-pointmult:
	$(BUILDER) 'testOnly lstm.PointMultTester'

test-pointadd:
	$(BUILDER) 'testOnly lstm.PointAddTester'

test-relu:
	$(BUILDER) 'testOnly lstm.ReLuTester'

test-tanh:
	$(BUILDER) 'testOnly lstm.TanhTester'

test-sigmoid:
	$(BUILDER) 'testOnly lstm.SigmoidTester'

test-fifo:
	$(BUILDER) 'test:runMain  lstm.fifo.FifoTester'

test-frontendrom:
	$(PYTHON) ./gen_synth_stft_files.py
	$(BUILDER) 'testOnly lstm.FrontendRomTester'

wave-relu:
	$(WAVE) test_run_dir/make_a_vcd/ReLu.vcd

wave-tanh:
	$(WAVE) test_run_dir/make_a_vcd/Tanh.vcd

wave-sigmoid:
	$(WAVE) test_run_dir/make_a_vcd/Sigmoid.vcd

wave-pointmult:
	$(WAVE) test_run_dir/make_a_vcd/pointmult.vcd

wave-pointadd:
	$(WAVE) test_run_dir/make_a_vcd/pointadd.vcd

wave-fifo:
	$(WAVE) test_run_dir/make_a_vcd/BubbleFifo.vcd

wave-vectorcat:
	$(WAVE) test_run_dir/make_a_vcd/VectorCat.vcd

wave-vectorreverse:
	$(WAVE) test_run_dir/make_a_vcd/VectorReverse.vcd

clean:
	rm -rf mem*.txt target test_run_dir
