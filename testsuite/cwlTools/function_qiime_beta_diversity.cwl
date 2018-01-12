---
cwlVersion: v1.0
baseCommand: beta_diversity_through_plots.py
inputs:
  input_otu_table:
    type: File
    inputBinding:
      prefix: '-i '
      separate: false
      position: 1
  output_beta_diversity_outputFileName:
    type: string
    inputBinding:
      prefix: '-o '
      position: 4
  parallel:
    type:
    - type: record
      fields:
        use_parallel:
          type: boolean
          inputBinding:
            prefix: '-a '
            separate: false
            position: 5
    - type: record
      fields:
        do_not_use_parallel:
          type: boolean
          inputBinding:
            position: 5
  input_mapping:
    type: File
    inputBinding:
      prefix: '-m '
      separate: false
      position: 2
  input_tree_file:
    type: File
    inputBinding:
      prefix: '-t '
      separate: false
      position: 3
  jobs_to_start:
    type: int
    inputBinding:
      prefix: '-O '
      separate: false
      position: 6
outputs:
  output_beta_diversity:
    type: ToolDependentRepresentation
hints:
  DockerRequirement:
    dockerPull: quay.io/biocontainers/qiime
class: CommandLineTool
