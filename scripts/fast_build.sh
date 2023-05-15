#!/bin/bash

ACG_HOME=$(pwd)
if [[ $1 == 'graal' ]]
then
    cd ../graal-simd/compiler
    mx build
    cd $ACG_HOME
    cd ../graaljs/graal-js
    mx build
    cd $ACG_HOME
fi
mx build
