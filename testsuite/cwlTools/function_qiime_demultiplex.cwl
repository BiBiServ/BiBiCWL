---
cwlVersion: v1.0
class: CommandLineTool
baseCommand: split_libraries_fastq.py
requirements:
  ShellCommandRequirement:
    class: ShellCommandRequirement
hints:
  DockerRequirement:
    class: DockerRequirement
    dockerPull: quay.io/biocontainers/qiime:1.9.1--np110py27_1
inputs:
  input_merged_reads:
    inputBinding:
      position: 1
      prefix: '-i '
      separate: false
      shellQuote: false
    type:
    - "null"
    - File[]
  phred_quality_threshold:
    inputBinding:
      position: 4
      prefix: '-q '
      separate: false
      shellQuote: false
    type:
    - int
    - "null"
  input_metadata_mapping_files:
    inputBinding:
      position: 7
      prefix: '-m '
      separate: false
      shellQuote: false
    type:
    - "null"
    - File
  sample_id:
    inputBinding:
      position: 2
      prefix: '--sample_id '
      separate: false
      shellQuote: false
    type:
    - string
    - "null"
  output_demultiplexed_directory_outputFileName:
    inputBinding:
      position: 3
      prefix: '-o '
      shellQuote: false
    type:
    - string
    - "null"
  barcode_type:
    inputBinding:
      position: 6
      prefix: '--barcode_type '
      separate: false
      shellQuote: false
    type:
    - string
    - "null"
  phred_offset:
    inputBinding:
      position: 5
      prefix: '--phred_offset '
      separate: false
      shellQuote: false
    type:
    - int
    - "null"
outputs:
  outputfile_demultiplexed_seqs:
    outputBinding:
      glob: seqs.fna
    type: File
  outputfile_demultiplex_split_library_log:
    outputBinding:
      glob: split_library_log.txt
    type: File
  outputfile_qiime_demultiplex_histograms:
    outputBinding:
      glob: histograms.txt
    type: File
