#!/usr/bin/python3

depth    = 8
length   = 4
bitwidth = 8

# one file per vector dimension
for i in range(length):
    with open(f"mem{i}.txt", 'w') as f:
        for j in range(depth):
            f.write(format(j * length + i, 'x'))
            f.write('\n')


