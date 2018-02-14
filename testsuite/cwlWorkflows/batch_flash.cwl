---
cwlVersion: v1.0
class: Workflow


inputs:
  flash_interleavedFASTQ: File
  flash_use_interleaved: boolean
  flash_density: float
  flash_use_outies: boolean
  flash_min_size: int
  flash_max_size: int




outputs:
  batch_extendedFrags:
    type: File
    outputSource: flash/extendedFrags
  batch_histogram:
    type: File
    outputSource: flash/histogram
  batch_hist:
    type: File
      outputSource: flash/hist
  batch_flash_stdout:
    type: File
    outputSource: flash/stdout
  batch_notCombined_1:
    type: File
    outputSource: flash/notCombined_1
  batch_notCombined_2:
    type: File
    outputSource: flash/notCombined_2



  
steps:
  flash: 
