---
cwlVersion: v1.0
class: CommandLineTool
baseCommand: summarize_taxa_through_plots.py
requirements:
  ShellCommandRequirement:
    class: ShellCommandRequirement
  EnvVarRequirement:
    envDef:
      MPLBACKEND: "Agg"
hints:
  DockerRequirement:
    class: DockerRequirement
    dockerPull: quay.io/biocontainers/qiime:1.9.1--np112py27_1
arguments:
- position: 3
  shellQuote: false
  valueFrom: -f -o taxa_plots
inputs:
  input_otu_table:
    inputBinding:
      position: 1
      prefix: '-i '
      separate: false
      shellQuote: false
    type: File
  input_mapping:
    inputBinding:
      position: 2
      prefix: '-m '
      separate: false
      shellQuote: false
    type: File
outputs:
  taxa_plots_directory:
    outputBinding:
      glob: taxa_plots
    type: Directory
stdout: $(inputs.PLACEHOLDER_output_taxa_plots_outputFileName)
