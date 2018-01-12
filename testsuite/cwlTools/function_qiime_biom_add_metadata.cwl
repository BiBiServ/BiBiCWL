---
cwlVersion: v1.0
baseCommand: biom add-metadata
inputs:
  input_sample_metadata:
    type: File
    inputBinding:
      prefix: '-m '
      separate: false
      position: 3
  output_biom_outputFileName:
    type: string
    inputBinding:
      prefix: '-o '
      position: 2
  input_open_reference_otus:
    type: File
    inputBinding:
      prefix: '-i '
      separate: false
      position: 1
outputs:
  output_biom:
    type: ToolDependentRepresentation
hints:
  DockerRequirement:
    dockerPull: quay.io/biocontainers/qiime
class: CommandLineTool
