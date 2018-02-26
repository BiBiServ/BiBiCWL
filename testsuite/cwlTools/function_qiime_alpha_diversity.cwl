---
cwlVersion: v1.0
class: CommandLineTool
baseCommand: alpha_rarefaction.py
requirements:
  ShellCommandRequirement:
    class: ShellCommandRequirement
hints:
  DockerRequirement:
    class: DockerRequirement
    dockerPull: jsaydo/qiime_matplotlib_backend_agg:latest
arguments:
- position: 7
  shellQuote: false
  valueFrom: -f -o alpha_out
inputs:
  input_otu_table:
    inputBinding:
      position: 1
      prefix: '-i '
      separate: false
      shellQuote: false
    type: File
  parallel:
    inputBinding:
      position: 5
      prefix: '-a '
      separate: false
      shellQuote: false
    type: boolean
  input_mapping:
    inputBinding:
      position: 2
      prefix: '-m '
      separate: false
      shellQuote: false
    type: File
  input_tree_file:
    inputBinding:
      position: 3
      prefix: '-t '
      separate: false
      shellQuote: false
    type: File
  jobs_to_start:
    inputBinding:
      position: 6
      prefix: '-O '
      separate: false
      shellQuote: false
    type: int
  input_parameter_file:
    inputBinding:
      position: 4
      prefix: '-p '
      separate: false
      shellQuote: false
    type: File
outputs:
  observed_otusunmodifiedidentifier:
    outputBinding:
      glob: observed_otusUnmodifiedIdentifier.png
    type: File
  chao1linkerprimersequence:
    outputBinding:
      glob: chao1LinkerPrimerSequence.png
    type: File
  chao1localisation:
    outputBinding:
      glob: chao1Localisation.png
    type: File
  observed_otusdescription:
    outputBinding:
      glob: observed_otusDescription.png
    type: File
  shannon:
    outputBinding:
      glob: shannon.txt
    type: File
  chao1description:
    outputBinding:
      glob: chao1Description.png
    type: File
  chao1barcodesequence:
    outputBinding:
      glob: chao1BarcodeSequence.png
    type: File
  log_20180216210531:
    outputBinding:
      glob: log_20180216210531.txt
    type: File
  shannonlinkerprimersequence:
    outputBinding:
      glob: shannonLinkerPrimerSequence.png
    type: File
  observed_otus:
    outputBinding:
      glob: observed_otus.txt
    type: File
  shannondescription:
    outputBinding:
      glob: shannonDescription.png
    type: File
  observed_otuslocalisation:
    outputBinding:
      glob: observed_otusLocalisation.png
    type: File
  chao1unmodifiedidentifier:
    outputBinding:
      glob: chao1UnmodifiedIdentifier.png
    type: File
  observed_otuslinkerprimersequence:
    outputBinding:
      glob: observed_otusLinkerPrimerSequence.png
    type: File
  chao1sampleid:
    outputBinding:
      glob: chao1SampleID.png
    type: File
  PLACEHOLDER_output_alpha_diversity:
    type: stdout
  shannonlocalisation:
    outputBinding:
      glob: shannonLocalisation.png
    type: File
  observed_otussampleid:
    outputBinding:
      glob: observed_otusSampleID.png
    type: File
  shannonbarcodesequence:
    outputBinding:
      glob: shannonBarcodeSequence.png
    type: File
  rarefaction_plots:
    outputBinding:
      glob: rarefaction_plots.html
    type: File
  shannonsampleid:
    outputBinding:
      glob: shannonSampleID.png
    type: File
  shannonunmodifiedidentifier:
    outputBinding:
      glob: shannonUnmodifiedIdentifier.png
    type: File
  chao1:
    outputBinding:
      glob: chao1.txt
    type: File
  observed_otusbarcodesequence:
    outputBinding:
      glob: observed_otusBarcodeSequence.png
    type: File
stdout: alpha_stdout.txt
