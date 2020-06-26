BUILDER:=sbt

test: test-relu test-pointmult test-tanh test-sigmoid test-pointadd test-fifo

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

