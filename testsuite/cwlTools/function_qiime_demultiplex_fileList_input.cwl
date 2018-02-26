---
cwlVersion: v1.0
baseCommand: split_libraries_fastq.py
inputs:
  input_merged_reads:
    type: File[]
    inputBinding:
      itemSeparator: ','
      prefix: '-i '
      separate: false
      position: 1
      shellQuote: false
  phred_quality_threshold:
    type: int
    inputBinding:
      prefix: '-q '
      separate: false
      position: 4
      shellQuote: false
  input_metadata_mapping_files:
    type: File
    inputBinding:
      prefix: '-m '
      separate: false
      position: 7
      shellQuote: false
  sample_id:
    type: string
    inputBinding:
      prefix: '--sample_id '
      separate: false
      position: 2
      shellQuote: false
  output_demultiplexed_directory_outputFileName:
    type: string
    inputBinding:
      prefix: '-o '
      position: 3
      shellQuote: false
  barcode_type:
    type: string
    inputBinding:
      prefix: '--barcode_type '
      separate: false
      position: 6
      shellQuote: false
  phred_offset:
    type: int
    inputBinding:
      prefix: '--phred_offset '
      separate: false
      position: 5
      shellQuote: false
outputs:
  outputfile_demultiplexed_seqs:
    type: File
    outputBinding:
      glob: seqs.fna
  outputfile_demultiplex_split_library_log:
    type: File
    outputBinding:
      glob: split_library_log.txt
  outputfile_qiime_demultiplex_histograms:
    type: File
    outputBinding:
      glob: histograms.txt
requirements:
  ShellCommandRequirement:
    class: ShellCommandRequirement
hints:
  DockerRequirement:
    dockerPull: quay.io/biocontainers/qiime:1.9.1--np110py27_1
    class: DockerRequirement
class: CommandLineTool
