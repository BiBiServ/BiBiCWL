#! /bin/bash

file=$1

flashInput=$2


# unzip input files
unzip $file

# use flash with parameters and unzipped files
flash2 $flashInput

