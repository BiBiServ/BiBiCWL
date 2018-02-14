---
cwlVersion: v1.0
class: CommandLineTool
baseCommand: pick_open_reference_otus.py
requirements:
  ShellCommandRequirement:
    class: ShellCommandRequirement
hints:
  DockerRequirement:
    class: DockerRequirement
    dockerPull: quay.io/biocontainers/qiime:1.9.1--np110py27_1
arguments:
- position: 9
  shellQuote: false
  valueFrom: -f -o /var/spool/cwl
inputs:
  input_splitted_libraries:
    inputBinding:
      position: 1
      prefix: '-i '
      separate: false
      shellQuote: false
    type:
    - "null"
    - File[]
  parallel:
    inputBinding:
      position: 2
      prefix: '-a '
      separate: false
      shellQuote: false
    type:
    - boolean
    - "null"
  otu_picking_method:
    inputBinding:
      position: 4
      prefix: '-m '
      separate: false
      shellQuote: false
    type:
    - string
    - "null"
  input_reference_sequences:
    inputBinding:
      position: 7
      prefix: '-r '
      separate: false
      shellQuote: false
    type:
    - "null"
    - File[]
  step_four:
    inputBinding:
      position: 8
      prefix: '--suppress_step4 '
      separate: false
      shellQuote: false
    type:
    - boolean
    - "null"
  jobs_to_start:
    inputBinding:
      position: 3
      prefix: '-O '
      separate: false
      shellQuote: false
    type:
    - int
    - "null"
  verbose:
    inputBinding:
      position: 5
      prefix: '-v '
      separate: false
      shellQuote: false
    type:
    - boolean
    - "null"
  input_parameter_file:
    inputBinding:
      position: 6
      prefix: '-p '
      separate: false
      shellQuote: false
    type:
    - "null"
    - File[]
outputs:
  rep_set_tre:
    outputBinding:
      glob: rep_set.tre
    type: File
  otu_table_mc2_w_tax_no_pynast_failures_biom:
    outputBinding:
      glob: otu_table_mc2_w_tax_no_pynast_failures.biom
    type: File
