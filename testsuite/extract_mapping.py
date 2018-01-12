#!/usr/bin/env python2.7
# coding=utf-8

"""
	16s_pyflow.py

	Date:   16/02/2016
	Usage:  For usage instructions run with option --help
	Author: Madis Rumming <mrumming@cebitec.uni-bielefeld.de>
"""



__author__  = "Madis Rumming <mrumming@cebitec.uni-bielefeld.de>"
__copyright__ = "Copyright 2016, Computational Metagenomics, Faculty of Technology, Bielefeld University"

__version__ = "1.1"
__maintainer__ = "Madis Rumming"
__email__ = "mrumming@cebitec.uni-bielefeld.de"
__status__ = "Production"


import argparse
import os.path
import sys
from string import maketrans

			
mapping_file = open(os.path.join(self.output_dir, "qiime", "combined_mapping.txt"), "w")
if metadata_header:
    mapping_file.write("#SampleID\tBarcodeSequence\tLinkerPrimerSequence\tLocalisation\tUnmodifiedIdentifier\t%s\tDescription\n" % ("\t".join(metadata_header)))
    for key in self.samples.keys():
        mapping_file.write("%s\t\t\t\t%s\t%s\tNoNe\n" % (key.translate(tr_table), key, "\t".join(metadata_[key.translate(tr_table)])))
else:
    mapping_file.write("#SampleID\tBarcodeSequence\tLinkerPrimerSequence\tLocalisation\tUnmodifiedIdentifier\tDescription\n")
    for key in self.samples.keys():
        mapping_file.write("%s\t\t\t\t%s\tNoNe\n" % (key.translate(tr_table), key))

mapping_file.close()
			
