#!/bin/bash
java -jar ../google-java-format-1.15.0-all-deps.jar --replace $(find . -type f -name "*.java" | grep ".*/src/.*java")
