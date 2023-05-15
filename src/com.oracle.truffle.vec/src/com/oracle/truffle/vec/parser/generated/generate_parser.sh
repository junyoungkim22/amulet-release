java -cp antlr-4.8-complete.jar org.antlr.v4.Tool Vec.g4 -visitor
sed "1s;^;package com.oracle.truffle.parser.generated\;\n;" -i *.java
