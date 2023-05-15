#!/bin/bash

TEST_DIR=tests/correctness_tests
LOG_NUM=0
LOG_FILE=$TEST_DIR/log.txt
rm $LOG_FILE

CleanLogs() {
  FILE_NAME=$1
  CHECK_DIR=$TEST_DIR/$FILE_NAME
  rm $CHECK_DIR/log*.txt
}

Test() {
  FILE_NAME=$1
  CHECK_DIR=$TEST_DIR/$FILE_NAME
  TEST_LOG_FILE=$CHECK_DIR/log"$LOG_NUM".txt
  $2 --file $CHECK_DIR/$FILE_NAME.js > $TEST_LOG_FILE
  grep "Passed Arguments" $TEST_LOG_FILE >> $LOG_FILE
  grep "Total time" $TEST_LOG_FILE >> $LOG_FILE
  grep "CORRECT" $TEST_LOG_FILE >> $LOG_FILE
  grep "WRONG" $TEST_LOG_FILE >> $LOG_FILE
  echo "" >> $LOG_FILE
  ((LOG_NUM=LOG_NUM+1))
}

CleanLogs 1d_small
Test 1d_small "numactl --i=0 ./run.sh --execmode 1 --chunksize 1000 --warmupsize 400 --partitiontype 1"
Test 1d_small "numactl --i=0 ./run.sh --execmode 1 --chunksize 1000 --warmupsize 1 --partitiontype 1"
Test 1d_small "numactl --i=0 ./run.sh --execmode 2 --numthreads 4 --loadsize 1000000 --chunksize 100 --warmupsize 40 --partitiontype 1"
Test 1d_small "numactl --i=0 ./run.sh --execmode 2 --numthreads 4 --loadsize 1000000 --chunksize 100 --warmupsize 1 --partitiontype 1"
Test 1d_small "numactl --i=0 ./run.sh --execmode 1 --loadsize 1000000 --chunksize 100 --warmupsize 40 --partitiontype 2 --cubesize 11"
Test 1d_small "numactl --i=0 ./run.sh --execmode 2 --numthreads 4 --loadsize 1000000 --chunksize 100 --warmupsize 40 --partitiontype 2 --cubesize 11"
Test 1d_small "numactl --i=0 ./run.sh --execmode 2 --numthreads 4 --loadsize 1000000 --chunksize 100 --warmupsize 1 --partitiontype 2 --cubesize 11"

CleanLogs 2d_small
Test 2d_small "numactl --i=0 ./run.sh --execmode 1 --chunksize 1000 --warmupsize 400 --partitiontype 1"
Test 2d_small "numactl --i=0 ./run.sh --execmode 1 --chunksize 1000 --warmupsize 1 --partitiontype 1"
Test 2d_small "numactl --i=0 ./run.sh --execmode 2 --numthreads 4 --loadsize 1000000 --chunksize 1000 --warmupsize 400 --partitiontype 1"
Test 2d_small "numactl --i=0 ./run.sh --execmode 2 --numthreads 4 --loadsize 1000000 --chunksize 1000 --warmupsize 1 --partitiontype 1"
Test 2d_small "numactl --i=0 ./run.sh --execmode 1 --loadsize 1000000 --chunksize 50 --warmupsize 40 --partitiontype 2 --cubesize 15 --plantypes NO_BRANCH"
Test 2d_small "numactl --i=0 ./run.sh --execmode 2 --numthreads 4 --loadsize 1000000 --chunksize 50 --warmupsize 38 --partitiontype 2 --cubesize 15 --plantypes NO_BRANCH"
Test 2d_small "numactl --i=0 ./run.sh --execmode 2 --numthreads 4 --loadsize 1000000 --chunksize 50 --warmupsize 1 --partitiontype 2 --cubesize 15 --plantypes NO_BRANCH"

#Test 1d_100 "numactl --i=0 ./run.sh --execmode 1 --chunksize 10000 --warmupsize 40000 --partitiontype 1"

cat $LOG_FILE

