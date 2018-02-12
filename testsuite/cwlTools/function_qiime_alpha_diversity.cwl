---
cwlVersion: v1.0
class: CommandLineTool
baseCommand: alpha_rarefaction.py
requirements:
  ShellCommandRequirement:
    class: ShellCommandRequirement
hints:
  DockerRequirement:
    class: DockerRequirement
    dockerPull: quay.io/biocontainers/qiime:1.9.1--np110py27_1
arguments:
- position: 7
  shellQuote: false
  valueFrom: -f -o /var/spool/cwl
inputs:
  input_otu_table:
    inputBinding:
      position: 1
      prefix: '-i '
      separate: false
      shellQuote: false
    type: File
  parallel:
    inputBinding:
      position: 5
      prefix: '-a '
      separate: false
      shellQuote: false
    type: boolean
  input_mapping:
    inputBinding:
      position: 2
      prefix: '-m '
      separate: false
      shellQuote: false
    type: File
  input_tree_file:
    inputBinding:
      position: 3
      prefix: '-t '
      separate: false
      shellQuote: false
    type: File
  jobs_to_start:
    inputBinding:
      position: 6
      prefix: '-O '
      separate: false
      shellQuote: false
    type: int
  input_parameter_file:
    inputBinding:
      position: 4
      prefix: '-p '
      separate: false
      shellQuote: false
    type: File
outputs: {}
