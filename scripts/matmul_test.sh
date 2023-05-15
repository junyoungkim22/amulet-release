#!/bin/bash
RESULT_DIR=$1
for nc in 16 32 64 128 256 512
do
	echo $nc
	echo
	for kc in 16 32 64 128 256 512
	do
	  NC="$nc"
	  KC="$kc"
    sed -i "2s/.*/var nc = ${NC}; /g" tests/simd/matmulkernel8x16/1dmatmul.js
    sed -i "1s/.*/var kc = ${KC}; /g" tests/simd/matmulkernel8x16/1dmatmul.js
    sh run.sh --warmupsize 1 --plantypes LOGICAL_AND --chunksize 100000000 --file tests/simd/matmulkernel8x16/1dmatmul.js > "$RESULT_DIR"/"${NC}_${KC}.txt"
	done
	echo
done