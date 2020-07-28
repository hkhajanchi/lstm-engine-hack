g!/bin/sh

docker run --user "$(id -u):$(id -g)" -v $PWD:/hack --workdir /hack -it h-chisel fish 
