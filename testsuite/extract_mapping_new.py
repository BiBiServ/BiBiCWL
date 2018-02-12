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





# Global paths to the binaries
flash = "/vol/cmg/bin/flash"
fastqc = "/vol/cmg/bin/fastqc"




def parse_arguments():
	parser = argparse.ArgumentParser("Performs analysis of CeBiTec sequenced 16s rRNA sequenced samples incl. read merging, simple QC, OTU clustering via QIIME 1.9 (open reference based), taxonomical assignment and basiv overview plots. It starts with scanning all subdirectories (each subdir is one sample with R001 and R002 forward/backward mate pair reads) from designated input direcotry and takes the directory name in a normalized form as sample identifier. For details of name conversion see description of --qiime-metadata option.")
	parser.add_argument("-i", "--input-dir", dest='input_dir', help="Path to home directory of sample subdirectories with .fastq(.gz). Default: %s" % (os.path.abspath('.')), default=os.path.abspath('.'), required=False, type=str)
	parser.add_argument("-o", "--output-dir", dest='output_dir', help="Path to final output directory to write results to. Default: %s" % (os.path.abspath('.')), default=os.path.abspath('.'), required=False, type=str)
    
	#parser.add_argument("--qiime-steps", dest='qiime_steps', help="Step 4 realted settings for OTU clustering. Default: %s" % ('suppress_step4'), choices=['with_step4', 'suppress_step4', 'both'], default='suppress_step4', required=False)
	parser.add_argument("--qiime-settings", dest='qiime_settings', help="Path to qiime parameter file (optional).", required=False, default=None, type=str)
	#parser.add_argument("--qiime-reference", dest='qiime_reference', help="Path to 16s fasta reference. Default as set in qiime default.", required=False, default=None, type=str)
	#parser.add_argument("--qiime-taxonomy", dest='qiime_taxonomy', help="Path to taxonomy for 16s reference DB. Default as set in qiime default.", required=False, default=None, type=str)
	parser.add_argument("--qiime-metadata", dest='qiime_metadata', help="Tab-separated metadata for all samples with QIIME-compatible normalized SampleIDs. 'a-z', 'A-Z' and '.' are allowed characters. Sample names as found in the input directory as subdirectories are normalized through replacing the following characters with a '.': _-+%%<BLANK WHITESPACE>;:,/ A header with speaking category names is required, starting with '#SampleID'. Allowed characters for the header are: 'a-z', 'A-Z', '0-9' and '_'. Example header: '#SampleID\\tCondition\\tMedication'", required=False, default=None, type=str)
	
	
	
	#p_group = parser.add_argument_group("Stop after", "Stop computation after the choosen step.")
	#group = p_group.add_mutually_exclusive_group(required=True)
	#group.add_argument("--readmerging", dest='stop_at', action='store_const', const=0)
	#group.add_argument("--qc", dest='stop_at', action='store_const', const=1)
	#group.add_argument("--demultiplexing", dest='stop_at', action='store_const', const=2)
	#group.add_argument("--clustering", dest='stop_at', action='store_const', const=3)
	#group.add_argument("--assign-taxnominy", dest='stop_at', action='store_const', const=4)
	#group.add_argument("--alpha-diversity", dest='stop_at', action='store_const', const=5)
	#group.add_argument("--beta-diversity", dest='stop_at', action='store_const', const=6)
	#group.add_argument("--complete", help="Perform the whole pipeline including plot generation.", dest='stop_at', action='store_const', const=7)
	
	#parser.add_argument("--cores", dest='nCores', help="Amount of CPUs to use for parallel jobs. Default: %i" % (48), default=48, required=False, type=int)
	#parser.add_argument("--is-continued", dest='continued', help="Enables continuing an erroneous or paused workflow. MUST use the same dataDirRoot as before.", default=False, required=False, action='store_true')
	#parser.add_argument("--is-dry-run", dest='dry_run', help="Check workflow without execution.", default=False, action='store_true', required=False)
	
	
	
	args = parser.parse_args()

	return(args)








# Perform demultiplexing
#def doStuff(stop_at, output_dir, samples, flash, flash_th, flash_min, flash_max, flash_outies, fastqc, qiime_steps, qiime_settings, qiime_reference, qiime_metadata, nCores):
def doStuff(output_dir, samples, qiime_settings, qiime_metadata):
	cmd_qiime_base = "source activate qiime1_9_1 && "
	tr_table = maketrans("_-+% ;:,/",".........")

	os.mkdir(os.path.join(output_dir, "qiime"))

	metadata_ = {}
	metadata_header = None

	if qiime_metadata:
		metadataIn = open(os.path.abspath(qiime_metadata))
		header = metadataIn.readline()
		header = header.strip().split('\t')
		metadata_header = header[1:]
		for line in metadataIn:
			line = line.strip().split('\t')
			metadata_[line[0]] = line[1:]
		metadataIn.close()




	mapping_file = open(os.path.join(output_dir, "qiime", "combined_mapping.txt"), "w")
	if metadata_header:
		mapping_file.write("#SampleID\tBarcodeSequence\tLinkerPrimerSequence\tLocalisation\tUnmodifiedIdentifier\t%s\tDescription\n" % ("\t".join(metadata_header)))
		for key in samples.keys():
			mapping_file.write("%s\t\t\t\t%s\t%s\tNoNe\n" % (key.translate(tr_table), key, "\t".join(metadata_[key.translate(tr_table)])))
	else:
		mapping_file.write("#SampleID\tBarcodeSequence\tLinkerPrimerSequence\tLocalisation\tUnmodifiedIdentifier\tDescription\n")
		for key in samples.keys():
			mapping_file.write("%s\t\t\t\t%s\tNoNe\n" % (key.translate(tr_table), key))

	mapping_file.close()

	alphaparam_file = open(os.path.join(output_dir, "qiime", "alpha_parameters.txt"), "w")
	alphaparam_file.write("alpha_diversity:metrics chao1,PD_whole_tree,observed_otus,shannon\n")
	alphaparam_file.close()

	keys_ = []
	files_ = []
	for key in samples.keys():
		keys_.append(key.translate(tr_table))
		files_.append(os.path.join(output_dir, "flash", "%s.extendedFrags.fastq" % (key)))

	print(" --sample_id %s" % (",".join(keys_)))

	print(samples)




def check_args(args):
#	if not os.path.exists(args.flash):
#		sys.exit("Specified binary of flash under %s does not exist." % (args.flash))
	if not os.path.exists(os.path.abspath(args.input_dir)):
		sys.exit("Specified input directory %s does not exist." % (args.input_dir))
	if not os.path.exists(os.path.abspath(args.output_dir)):
		sys.exit("Specified output directory %s does not exist." % (args.output_dir))
	if args.qiime_settings:
		if not os.path.exists(os.path.abspath(args.qiime_settings)):
			sys.exit("Specified qiime settings file %s does not exist." % (args.qiime_settings))
	if args.qiime_metadata:
		if not os.path.exists(os.path.abspath(args.qiime_metadata)):
			sys.exit("Specified qiime metadata file %s does not exist." % (args.qiime_metadata))
		else:
			metadata = open(os.path.abspath(args.qiime_metadata))
			err_string = []
			header = metadata.readline()
			header = header.strip().split("\t")
			if not len(header) > 1:
				err_string.append("No tab '\t' as separator found in header")
			if not header[0][0] == "#":
				err_string.append("Header must start with a number sign '#'")
			if not header[0] == "#SampleID":
				err_string.append("Header must start with '#SampleID'")
			ind = 1
			for line in metadata:
				line = line.strip().split("\t")
				if not len(line) == len(header):
					err_string.append("Not enough values in line #%i. Must match with column count in header." % (ind))
				ind += 1
			metadata.close()
			if not len(err_string) == 0:
				sys.exit("Errors found in metadata file:\n  %s\n" % ("\n  ".join(err_string)))






def main():
	
	args = parse_arguments()
	check_args(args)

	samples = {}
	if args.fastq_per_dir:
		for entry in os.listdir(args.input_dir):
			if os.path.isdir(os.path.join(args.input_dir, entry)):
				files = os.listdir(os.path.join(args.input_dir, entry))
				files = filter(lambda s : s.endswith(".fastq.gz"), files)
				if len(files) == 2:
					samples[entry] = [os.path.join(args.input_dir, entry, file_) for file_ in files]
	else:
		files = os.listdir(args.input_dir)
		files = filter(lambda s : s.endswith(".fastq.gz"), files)
		files.sort()
		for i in xrange(len(files)/2):
			samplename = "_".join(files[i*2].split(".")[0].split("_")[:-1])
			samples[samplename] = [os.path.join(args.input_dir, files[i*2]), os.path.join(args.input_dir, files[i*2+1])]
			
	doStuff(args.output_dir, samples, args.qiime_settings, args.qiime_metadata)
#	sys.exit(retval)



if __name__ == "__main__":
	main()
