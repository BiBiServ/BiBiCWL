---
cwlVersion: v1.0
class: CommandLineTool
baseCommand: biom
requirements:
  ShellCommandRequirement:
    class: ShellCommandRequirement
hints:
  DockerRequirement:
    class: DockerRequirement
    dockerPull: quay.io/biocontainers/qiime:1.9.1--np110py27_1
arguments:
- position: 1
  shellQuote: false
  valueFrom: add-metadata
inputs:
  input_metadata_mapping_files:
    inputBinding:
      position: 3
      prefix: '-m '
      separate: false
      shellQuote: false
    type:
    - "null"
    - File
  input_out_table_mc2_w_tax_no_pynast_failures_biom:
    inputBinding:
      position: 2
      prefix: '-i '
      separate: false
      shellQuote: false
    type:
    - "null"
    - File
  output_biom_outputFileName:
    inputBinding:
      position: 4
      prefix: '-o '
      shellQuote: false
    type:
    - string
    - "null"
outputs:
  output_biom:
    outputBinding:
      glob: $(inputs.output_biom_outputFileName)
    type: File
