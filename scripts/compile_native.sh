#!/bin/bash
. app.config
if [[ "$arch" == "avx512" ]]; then
  gcc -O3 -mavx -march=native -lc -shared -I$JAVA_HOME/include -I$JAVA_HOME/include/linux -o native/libUtils.so native/Utils_avx512.c
else
	gcc -O3 -mavx -march=native -lc -shared -I$JAVA_HOME/include -I$JAVA_HOME/include/linux -o native/libUtils.so native/Utils.c
fi

