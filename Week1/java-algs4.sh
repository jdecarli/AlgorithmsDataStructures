#!/bin/bash

# echo "argument is: " $1
java -cp ".lift/algs4.jar" $1

# ---------------------------------------------
# This must match the install directory
# INSTALL=/usr/local/algs4

# Sets the path to the classpath libraries
# CLASSPATH=(.:${INSTALL}/algs4.jar)

# Add algs4.jar to classpath and various commmand-line options.
# javac -cp "${CLASSPATH}" -g -encoding UTF-8 -Xlint:all -Xlint:-overrides -Xdiags:verbose -Xmaxwarns 10 -Xmaxerrs 10  "$@"
