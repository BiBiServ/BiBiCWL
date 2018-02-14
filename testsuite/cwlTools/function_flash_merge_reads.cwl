---
cwlVersion: v1.0
class: CommandLineTool
baseCommand: flash2
hints:
  DockerRequirement:
    class: DockerRequirement
    dockerPull: jsaydo/flash2:v2.2.00
inputs:
  interleaved_input:
    inputBinding:
      position: 7
      prefix: '--interleaved-input '
      separate: false
    type: boolean
  fastq_2:
    inputBinding:
      position: 2
    type: ["null", File]
  density:
    inputBinding:
      position: 5
      prefix: '-x '
      separate: false
    type: float
  use_outies:
    inputBinding:
      position: 6
      prefix: '-O '
      separate: false
    type: boolean
  min_size:
    inputBinding:
      position: 3
      prefix: '-m '
      separate: false
    type: int
  fastq_1:
    inputBinding:
      position: 1
    type: File
  max_size:
    inputBinding:
      position: 4
      prefix: '-M '
      separate: false
    type: int
outputs:
  extendedFrags:
    outputBinding:
      glob: out.extendedFrags.fastq
    type: File
  histogram:
    outputBinding:
      glob: out.histogram
    type: File
  hist:
    outputBinding:
      glob: out.hist
    type: File
  flash_stdout:
    type: stdout
  notCombined_1:
    outputBinding:
      glob: out.notCombined_1.fastq
    type: File
  notCombined_2:
    outputBinding:
      glob: out.notCombined_2.fastq
    type: File
stdout: flash_stdout.txt
