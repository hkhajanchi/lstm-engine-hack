IIT LSTM Engine

# Building Source Files and Testbenches
Use the Python utility for automatically creating source file and testbench skeletons in the respective '/src/main' and '/src/test' directories. 
These files have the Chisel boilerplate already set up, so all you need to do is add your logic and simulate. 

To use, call 'make new-module MOD=*your module name*'. You should use one word module names, such as *foo* or *fooPipelined*, etc. 

The utility will create a file called '*your-module-name*.scala' in '/src/main/..' and '*your-module-name*Tester.scala' in 'src/test/..'
Additionally, the 'Makefile' in the base will get updated to include a simulation command. To simulate, call 
'make test-*your-module-name*' 


# Running Simulations

## Docker Setup
Simulations are run in a Docker container. Build the docker image using the specified Dockerfile, and run 
'./start\_docker.sh' to build a container that has the LSTM source tree mounted. 

Within the docker container, you can run different 'make' commands to simulate different modules. 

## Viewing Waveforms
To view waveforms for the design, you need to ensure that '--generate-vcd-output' is enabled in your Chisel3 testbench. 
(If you're using the Python utility for creating source files & testbenches, this is enabled by default) 

After simulation is completed, navigate to '/test/vcd/*your-module-name*' and run 'gtkwave *your-module-name*.vcd'. 
Note that this must be done within a Bash shell outside of Docker, and GTKWave must already be installed. 
GTKWave is GUI-based, so X-forwarding must be enabled if you're running this on a server. 

# License
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org/>
