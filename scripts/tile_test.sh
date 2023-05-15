#!/bin/bash
RESULT_DIR=$1
for nc in 144 288 576 2304 4608
do
	echo $nc
	echo
	for kc in 16 32 64 128 256 512
	do
	  NC="$nc"
	  KC="$kc"
    sed -i "2s/.*/var nc = ${NC}; /g" tests/simd/goto12x16.js
    sed -i "1s/.*/var kc = ${KC}; /g" tests/simd/goto12x16.js
    sh run.sh --warmupsize 1 --plantypes LOGICAL_AND --chunksize 100000000 --file tests/simd/goto12x16.js > "$RESULT_DIR"/"${NC}_${KC}.txt"
	done
	echo
done
