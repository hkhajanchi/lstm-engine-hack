
import argparse 
import os

def make_module_skeleton(module_base_path, src_dir, module_name): 

    try: 
        f=open(module_base_path,'r') 
    except:
        print("could not find module_template.txt") 

    data = f.read()
    mod  = data.replace('<<>>',module_name) 
    f.close()

    l = open(src_dir+module_name+".scala",'w')
    l.write(mod) 
    l.close() 

def make_tb_skeleton(tb_base_path, tb_dir, module_name):
    try:
        f = open(tb_base_path, 'r') 
    except:
        print("could not find base_tb.txt") 

    data = f.read()
    mod = data.replace("<<>>",module_name) 
    f.close()

    l = open(tb_dir+module_name+"Tester.scala",'w') 
    l.write(mod) 
    l.close()

def update_makefile(makefile_path, module_name): 
    try: 
        f= open(makefile_path, 'r') 
    except: 
        print("could not find Makefile") 
    
    make_cmd_0 = "test-{}:\n".format(module_name) 
    make_cmd_1 = "\t$(BUILDER) \'sbt testOnly lstm.{}Tester\'".format(module_name) 

    data = f.read()
    f.close()

    l = open(makefile_path, 'w')
    l.write(data)
    l.write(make_cmd_0)
    l.write(make_cmd_1) 
    l.close() 

if __name__ == "__main__":

    tb_base_path = os.path.expanduser('~'+'/lstm-engine/templates/tb.txt') 
    tb_dir = os.path.expanduser('~' + '/lstm-engine/src/test/scala/lstm/') 

    module_base_path = os.path.expanduser('~'+'/lstm-engine/templates/module.txt') 
    src_dir = os.path.expanduser('~'+'/lstm-engine/src/main/scala/lstm/')

    makefile_path = os.path.expanduser('~'+'/lstm-engine/Makefile') 

    parser = argparse.ArgumentParser(description="Generate Chisel3 Module and Testbench skeleton. Creates a Chisel3 module module_name.scala in the main directory and module_nameTester.scala in the test directory. Also updates the Makefile to add a command for testing your module")
    parser.add_argument("-n","--module_name",required=True, help="Name of Chisel module to be tested") 

    args = parser.parse_args()
    make_module_skeleton(module_base_path, src_dir, args.module_name) 
    make_tb_skeleton(tb_base_path, tb_dir, args.module_name) 
    update_makefile(makefile_path, args.module_name) 




     
