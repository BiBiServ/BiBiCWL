---
cwlVersion: v1.0
class: Workflow




requirements:
  - class: SubworkflowFeatureRequirement
  - class: ScatterFeatureRequirement





inputs:
  flash_interleavedFASTQ: File[]
  flash_use_interleaved: boolean
  flash_density: float
  flash_use_outies: boolean
  flash_min_size: int
  flash_max_size: int




outputs:
  batch_extendedFrags:
    type: File[]
    outputSource: flash/extendedFrags
  batch_histogram:
    type: File[]
    outputSource: flash/histogram
  batch_hist:
    type: File[]
    outputSource: flash/hist
  batch_flash_stdout:
    type: File[]
    outputSource: flash/flash_stdout
  batch_notCombined_1:
    type: File[]
    outputSource: flash/notCombined_1
  batch_notCombined_2:
    type: File[]
    outputSource: flash/notCombined_2



  
steps:
  flash:
    run: ../cwlTools/function_flash_merge_reads.cwl
    in:
      interleaved_input: flash_use_interleaved
      density: flash_density
      use_outies: flash_use_outies
      min_size: flash_min_size
      fastq_1: flash_interleavedFASTQ
      max_size: flash_max_size
    out:
      - extendedFrags
      - histogram
      - hist
      - flash_stdout
      - notCombined_1
      - notCombined_2
    scatter:
      - fastq_1
