#!/bin/bash

ACG_HOME=$(pwd)
if [[ $1 == 'graal' ]]
then
	cd ../graal-simd/compiler
	mx build
	cd $ACG_HOME
	cd ../graaljs/graal-js
	mx --dy /substratevm --native-images=lib:jvmcicompiler build
	cd $ACG_HOME
fi

if [[ $1 == 'parser' ]]
then
  cd src/com.oracle.truffle.vec/src/com/oracle/truffle/vec/parser/generated
  curl -O https://www.antlr.org/download/antlr-4.7-complete.jar
  java -cp antlr-4.7-complete.jar org.antlr.v4.Tool Vec.g4 -visitor
  sed "1s;^;package com.oracle.truffle.parser.generated\;\n;" -i *.java
  exit 0
fi

mx build
cd ../graaljs/graal-js
if [[ -z "${ACG_GRAALVM_HOME}" ]]
then
	export ACG_GRAALVM_HOME=$(mx --dy /substratevm,vec --native-images=lib:jvmcicompiler graalvm-home)
fi
cd $ACG_GRAALVM_HOME/jre/languages
if [[ ! -d vec ]]
then
	mkdir vec
fi
cp $ACG_HOME/mxbuild/dists/jdk1.8/vec.jar vec
cd $ACG_HOME
if [[ $1 == 'check' ]]
then
	./correctness_check.sh
fi
