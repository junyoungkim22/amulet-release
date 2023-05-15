#!/bin/bash

FILE=$1
MODE=$2
if [ -z "$2" ]
then
	MODE=1
fi
python mapper/map.py $FILE
taskset -c 0 mx -v --jdk=jvmci js --polyglot \
-ea -cp mxbuild/dists/jdk1.8/vec.jar --experimental-options --engine.BackgroundCompilation=false  --engine.TraceCompilationDetails \
-Xmx4g -Xms4g -DexecutionMode=$MODE -DchunkSize=10000000 -DplanTypes=LOGICAL_AND -DwarmupSize=1 -XX:-UseCompressedOops -XX:+UseLargePages -XX:JVMCINMethodSizeLimit=1000000 -Djava.library.path=$PROJECT_HOME/adaptive-code-generation/native $FILE.tmp
rm $FILE.tmp
