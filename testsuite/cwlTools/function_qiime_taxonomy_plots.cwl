---
cwlVersion: v1.0
baseCommand: summarize_taxa_through_plots.py
inputs:
  input_otu_table:
    type: File
    inputBinding:
      prefix: '-i '
      separate: false
      position: 1
      shellQuote: false
  input_mapping:
    type: File
    inputBinding:
      prefix: '-m '
      separate: false
      position: 2
      shellQuote: false
  input_parameter_file:
    type: File
    inputBinding:
      prefix: '-p '
      separate: false
      position: 3
      shellQuote: false
outputs:
  outputfile_taxa_plots:
    type: File
    outputBinding:
      glob: '*'
requirements:
  ShellCommandRequirement:
    class: ShellCommandRequirement
hints:
  DockerRequirement:
    dockerPull: quay.io/biocontainers/qiime:1.9.1--np110py27_1
    class: DockerRequirement
class: CommandLineTool
