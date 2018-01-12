---
cwlVersion: v1.0
baseCommand: fastqc
inputs:
  input_fastqc:
    type: File
    inputBinding:
      position: 2
outputs:
  output_fastqc:
    type: File
  outputfile_fastqc_zip:
    type: File
    outputBinding:
      glob: "*.zip"
hints:
  DockerRequirement:
    dockerPull: quay.io/biocontainers/fastqc:0.11.6--pl5.22.0_0
class: CommandLineTool
