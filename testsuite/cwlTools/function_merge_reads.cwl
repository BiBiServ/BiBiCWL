---
cwlVersion: v1.0
baseCommand: flash2
stdout: $(inputs.flash_stdout_outputFileName)
inputs:
  fastq_2:
    type: File
    inputBinding:
      position: 2
  density:
    type: float
    inputBinding:
      prefix: '-x '
      separate: false
      position: 5
  use_outies:
    type: boolean
    inputBinding:
      prefix: '-O '
      separate: false
      position: 6
  min_size:
    type: int
    inputBinding:
      prefix: '-m '
      separate: false
      position: 3
  fastq_1:
    type: File
    inputBinding:
      position: 1
  max_size:
    type: int
    inputBinding:
      prefix: '-M '
      separate: false
      position: 4
outputs:
  extendedFrags:
    type: File
    outputBinding:
      glob: out.extendedFrags.fastq
  histogram:
    type: File
    outputBinding:
      glob: out.histogram
  hist:
    type: File
    outputBinding:
      glob: out.hist
  flash_stdout:
    type: stdout
  notCombined_1:
    type: File
    outputBinding:
      glob: out.notCombined_1.fastq
  notCombined_2:
    type: File
    outputBinding:
      glob: out.notCombined_2.fastq
hints:
  DockerRequirement:
    dockerPull: jsaydo/flash2:v2.2.00
class: CommandLineTool
