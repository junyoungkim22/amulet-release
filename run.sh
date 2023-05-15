#!/bin/bash

FILE=unset

PROG_ARGS=""

usage(){
  echo "Usage: ./run.sh [ --file filename] [ --execmode 1 or 2 or 3 or 4 ] [ --chunksize N ] [ --warmupsize N ] [ --plantypes CSV of types of plan (e.g. LOGICAL_AND,NO_BRANCH)]
                        [ --partitiontype 1 or 2 ] [ --cubesize N ] [ --numthreads N ] [ --loadsize N ] [ --parallelexplore N ] [ --parallelexploit N ]"
  exit 2
}

PARSED_ARGUMENTS=$(getopt -n run.sh filename:,execmode:,chunksize:,warmupsize:,partitiontype:,cubesize:,numthreads:,loadsize:,parallelexplore:,parallelexploit: -- "$@")
eval set "$PARSED_ARGUMENTS" --

echo "Passed Arguments: $PARSED_ARGUMENTS"
while :
do
  case "$1" in
    --file) FILE=$2 ; shift 2 ;;
    --execmode) PROG_ARGS="${PROG_ARGS} --vm.DexecutionMode=$2" ; shift 2 ;;
    --chunksize) PROG_ARGS="${PROG_ARGS} --vm.DchunkSize=$2" ; shift 2 ;;
    --warmupsize) PROG_ARGS="${PROG_ARGS} --vm.DwarmupSize=$2" ; shift 2 ;;
    --partitiontype) PROG_ARGS="${PROG_ARGS} --vm.DpartitionType=$2" ; shift 2 ;;
    --cubesize) PROG_ARGS="${PROG_ARGS} --vm.DcubeSize=$2" ; shift 2 ;;
    --numthreads) PROG_ARGS="${PROG_ARGS} --vm.DnumberOfThreads=$2" ; shift 2 ;;
    --loadsize) PROG_ARGS="${PROG_ARGS} --vm.DloadSize=$2" ; shift 2 ;;
    --parallelexplore) PROG_ARGS="${PROG_ARGS} --vm.DparallelExplore=$2" ; shift 2 ;;
    --parallelexploit) PROG_ARGS="${PROG_ARGS} --vm.DparallelExploit=$2" ; shift 2 ;;
    --plantypes ) PROG_ARGS="${PROG_ARGS} --vm.DplanTypes=$2" ; shift 2 ;;
    --) shift; break;;
    *) echo "Unexpected option $1: make sure to specifiy options with spaces (and not =)"
      usage;;
  esac
done

python mapper/map.py $FILE
ACG_HOME=$(pwd)
cd ../graaljs/graal-js
ACG_GRAALVM_HOME=$(mx --dy /substratevm,vec --native-images=lib:jvmcicompiler graalvm-home)
cd $ACG_HOME
HEAP_SIZE=160
$ACG_GRAALVM_HOME/jre/languages/js/bin/js  --polyglot \
--experimental-options --engine.BackgroundCompilation=false \
--engine.TraceCompilationDetails --vm.Xms"$HEAP_SIZE"g --vm.Xmx"$HEAP_SIZE"g --vm.XX:-UseCompressedOops --vm.XX:+UseLargePages --vm.XX:+PrintGC --vm.XX:JVMCINMethodSizeLimit=10000000 $PROG_ARGS $FILE.tmp
#--engine.TraceCompilationDetails --vm.XX:+UseG1GC --vm.Xms4g --vm.Xmx4g --vm.XX:-UseCompressedOops --vm.XX:+UseLargePages --vm.XX:+PrintGC $PROG_ARGS $FILE.tmp
rm $FILE.tmp
