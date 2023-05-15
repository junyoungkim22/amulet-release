#!/bin/bash

RDIR="packed_thres"
for i in 2 4 8 16
do
  for j in 1 2
  do
    taskset -c 0-"$(($i - 1))" ./run.sh --file tests/simd/decl/general_2/ab.js --execmode 1 --numthreads $i --plantypes LOGICAL_AND > parallel_results/$RDIR/$j/$i.txt
  done
done

#taskset -c 0-"$(($1 - 1))" ./run.sh --file tests/simd/decl/general_2/ab.js --execmode 1 --numthreads $1 --plantypes LOGICAL_AND
