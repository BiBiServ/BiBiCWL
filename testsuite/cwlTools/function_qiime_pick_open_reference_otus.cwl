---
cwlVersion: v1.0
baseCommand: pick_open_reference_otus.py
inputs:
  input_splitted_libraries:
    type:
    - "null"
    - File
    inputBinding:
      prefix: '-i '
      separate: false
      position: 1
      shellQuote: false
  parallel:
    type: boolean
    inputBinding:
      prefix: '-a '
      separate: false
      position: 2
      shellQuote: false
  otu_picking_method:
    type: string
    inputBinding:
      prefix: '-m '
      separate: false
      position: 4
      shellQuote: false
  input_reference_sequences:
    type:
    - "null"
    - File
    inputBinding:
      prefix: '-r '
      separate: false
      position: 7
      shellQuote: false
  step_four:
    type: boolean
    inputBinding:
      prefix: '--suppress_step4 '
      separate: false
      position: 8
      shellQuote: false
  jobs_to_start:
    type: int
    inputBinding:
      prefix: '-O '
      separate: false
      position: 3
      shellQuote: false
  verbose:
    type: boolean
    inputBinding:
      prefix: '-v '
      separate: false
      position: 5
      shellQuote: false
  input_parameter_file:
    type:
    - "null"
    - File
    inputBinding:
      prefix: '-p '
      separate: false
      position: 6
      shellQuote: false
outputs:
  rep_set_tre:
    type: File
    outputBinding:
      glob: rep_set.tre
  otu_table_mc2_w_tax_no_pynast_failures_biom:
    type: File
    outputBinding:
      glob: otu_table_mc2_w_tax_no_pynast_failures.biom
requirements:
  ShellCommandRequirement:
    class: ShellCommandRequirement
arguments:
- valueFrom: -f -o /var/spool/cwl
  position: 9
  shellQuote: false
hints:
  DockerRequirement:
    dockerPull: quay.io/biocontainers/qiime:1.9.1--np110py27_1
    class: DockerRequirement
class: CommandLineTool
