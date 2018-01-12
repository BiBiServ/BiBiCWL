---
cwlVersion: v1.0
baseCommand: pick_open_reference_otus.py
inputs:
  output_open_reference_otus_outputFileName:
    type: string
    inputBinding:
      prefix: '-o '
      position: 8
  input_splitted_libraries:
    type: File
    inputBinding:
      prefix: '-i '
      separate: false
      position: 1
  parallel:
    type:
    - type: record
      fields:
        use_parallel:
          type: boolean
          inputBinding:
            prefix: '-a '
            separate: false
            position: 2
    - type: record
      fields:
        do_not_use_parallel:
          type: boolean
          inputBinding:
            position: 2
  otu_picking_method:
    type: string
    inputBinding:
      prefix: '-m '
      separate: false
      position: 4
  input_reference_sequences:
    type: File
    inputBinding:
      prefix: '-r '
      separate: false
      position: 7
  step_four:
    type:
    - type: record
      fields:
        suppress_step_four:
          type: boolean
          inputBinding:
            prefix: '--suppress-step4 '
            separate: false
            position: 9
    - type: record
      fields:
        do_not_suppress_step_four:
          type: boolean
          inputBinding:
            position: 9
  jobs_to_start:
    type: int
    inputBinding:
      prefix: '-O '
      separate: false
      position: 3
  verbose:
    type:
    - type: record
      fields:
        do_not_use_verbose:
          type: boolean
          inputBinding:
            position: 5
    - type: record
      fields:
        use_verbose:
          type: boolean
          inputBinding:
            prefix: '-v '
            separate: false
            position: 5
  input_parameter_file:
    type: File
    inputBinding:
      prefix: '-p '
      separate: false
      position: 6
outputs:
  output_open_reference_otus:
    type: ToolDependentRepresentation
hints:
  DockerRequirement:
    dockerPull: quay.io/biocontainers/qiime
class: CommandLineTool
