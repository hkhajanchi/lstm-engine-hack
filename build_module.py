import argparse 
import os


"""
Helper script to automatically build a Chisel module skeleton 
Takes user input for module name and output file name 
"""

module_base_path = os.path.expanduser('~'+'/lstm-engine/templates/module.txt') 
src_dir = os.path.expanduser('~'+'/lstm-engine/src/main/scala/lstm/')

parser = argparse.ArgumentParser(description="Generate Chisel3 Module skeleton") 
parser.add_argument("-n","--module_name",required=True, help="Name of Chisel module to be generated") 
parser.add_argument("-d", "--dest_name", required=True, help="Output filename") 

args = parser.parse_args()

try: 
    f=open(module_base_path,'r') 
except:
    print("could not find module_template.txt") 

data = f.read()
mod  = data.replace('<<>>',args.module_name) 
f.close()

l = open(src_dir+args.dest_name,'w')
l.write(mod) 
l.close() 


