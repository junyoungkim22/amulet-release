#!/bin/bash

taskset -c $3 ./run.sh --plantypes LOGICAL_AND --warmupsize 1 --file $1 --execmode $2
