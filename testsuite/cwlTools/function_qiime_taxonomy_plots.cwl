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
  input_mapping:
    type: File
    inputBinding:
      prefix: '-m '
      separate: false
      position: 3
  output_taxa_plots_outputFileName:
    type: string
    inputBinding:
      prefix: '-o '
      position: 2
  input_parameter_file:
    type: File
    inputBinding:
      position: 4
outputs:
  output_taxa_plots:
    type: ToolDependentRepresentation
hints:
  DockerRequirement:
    dockerPull: quay.io/biocontainers/qiime
class: CommandLineTool
