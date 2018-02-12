---
cwlVersion: v1.0
baseCommand: docker_run.bash
stdout: $(inputs.blast_output_blast_output_outputFileName)
inputs:
  blast_parameter_gap_open:
    type: int
    inputBinding:
      prefix: '-gapopen '
      separate: false
      position: 3
  blast_parameter_algorithm:
    type:
    - type: record
      fields:
        blastn:
          type: boolean
          inputBinding:
            prefix: ' -task blastn'
            separate: false
            position: 6
    - type: record
      fields:
        megablast:
          type: boolean
          inputBinding:
            prefix: ' -task megablast'
            separate: false
            position: 6
  blast_parameter_mismatch_penalty:
    type: int
    inputBinding:
      prefix: '-penalty '
      separate: false
      position: 4
  blast_parameter_gap_extend:
    type: int
    inputBinding:
      prefix: '-gapextend '
      separate: false
      position: 5
  blast_input_nucleotide_search_sequence:
    type: File
    inputBinding:
      prefix: '-y '
      separate: false
      position: 9
    fileType: Fasta_NAamb
  blast_parameter_match_reward:
    type: int
    inputBinding:
      prefix: '-reward '
      separate: false
      position: 2
  blast_parameter_reported_hits:
    type: int
    inputBinding:
      prefix: '-max_target_seqs '
      separate: false
      position: 7
outputs:
  blast_additionaloutput_krona:
    type: File
    outputBinding:
      glob: result.krona.html
  blast_output_blast_output:
    type: stdout
arguments:
- valueFrom: -p -c "blastn
  position: 1
- valueFrom: '"'
  position: 8
class: CommandLineTool
