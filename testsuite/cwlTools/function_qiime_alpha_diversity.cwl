---
cwlVersion: v1.0
baseCommand: alpha_rarefaction.py
inputs:
  input_otu_table:
    type: File
    inputBinding:
      prefix: '-i '
      separate: false
      position: 1
      shellQuote: false
  PLACEHOLDER_output_alpha_diversity_outputFileName:
    type: string
    inputBinding:
      position: 8
      shellQuote: false
  parallel:
    type: boolean
    inputBinding:
      prefix: '-a '
      separate: false
      position: 5
      shellQuote: false
  input_mapping:
    type: File
    inputBinding:
      prefix: '-m '
      separate: false
      position: 2
      shellQuote: false
  input_tree_file:
    type: File
    inputBinding:
      prefix: '-t '
      separate: false
      position: 3
      shellQuote: false
  jobs_to_start:
    type: int
    inputBinding:
      prefix: '-O '
      separate: false
      position: 6
      shellQuote: false
  input_parameter_file:
    type: File
    inputBinding:
      prefix: '-p '
      separate: false
      position: 4
      shellQuote: false
outputs: {}
requirements:
  ShellCommandRequirement:
    class: ShellCommandRequirement
arguments:
- valueFrom: -f -o /var/spool/cwl
  position: 7
  shellQuote: false
hints:
  DockerRequirement:
    dockerPull: quay.io/biocontainers/qiime:1.9.1--np110py27_1
    class: DockerRequirement
class: CommandLineTool
