import argparse 
import os

"""
simple helper script to parse the Chisel testbench base and replace empty module declarations with the module of choice
"""
tb_base_path = os.path.expanduser('~'+'/lstm-engine/templates/tb.txt') 
tb_dir = os.path.expanduser('~' + '/lstm-engine/src/test/scala/lstm/') 

parser = argparse.ArgumentParser(description="Generate Chisel3 Testbench skeleton") 
parser.add_argument("-n","--module_name",required=True, help="Name of Chisel module to be tested") 
parser.add_argument("-d", "--dest_name", required=True, help="Output filename") 

args = parser.parse_args()
 
try:
    f = open(tb_base_path, 'r') 
except:
    print("could not find base_tb.txt") 

data = f.read()
mod = data.replace("<<>>",args.module_name) 
f.close()

l = open(tb_dir+args.dest_name,'w') 
l.write(mod) 
l.close()


