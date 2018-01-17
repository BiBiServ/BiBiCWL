---
cwlVersion: v1.0
baseCommand: biom
inputs:
  input_metadata_mapping_files:
    type: File
    inputBinding:
      prefix: '-m '
      separate: false
      position: 3
      shellQuote: false
  input_out_table_mc2_w_tax_no_pynast_failures_biom:
    type: File
    inputBinding:
      prefix: '-i '
      separate: false
      position: 2
      shellQuote: false
  output_biom_outputFileName:
    type: string
    inputBinding:
      prefix: '-o '
      position: 4
      shellQuote: false
outputs:
  output_biom:
    type: File
    outputBinding:
      glob: $(inputs.output_biom_outputFileName)
requirements:
  ShellCommandRequirement:
    class: ShellCommandRequirement
arguments:
- valueFrom: add-metadata
  position: 1
  shellQuote: false
hints:
  DockerRequirement:
    dockerPull: quay.io/biocontainers/qiime:1.9.1--np110py27_1
    class: DockerRequirement
class: CommandLineTool
