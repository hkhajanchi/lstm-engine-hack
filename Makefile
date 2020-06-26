
test: test-relu

test-relu:
	sbt 'testOnly lstm.ReLuTester'

test-tanh:
	sbt 'testOnly lstm.tanhTester'
