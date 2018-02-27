---
cwlVersion: v1.0
class: CommandLineTool
baseCommand: alpha_rarefaction.py
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
      glob: alpha_out/alpha_rarefaction_plots/average_plots/observed_otusUnmodifiedIdentifier.png
    type: File
  chao1linkerprimersequence:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/chao1LinkerPrimerSequence.png
    type: File
  chao1localisation:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/chao1Localisation.png
    type: File
  chao1barcodesequence:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/chao1BarcodeSequence.png
    type: File
  shannonlinkerprimersequence:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/shannonLinkerPrimerSequence.png
    type: File
  observed_otuslocalisation:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/observed_otusLocalisation.png
    type: File
  chao1unmodifiedidentifier:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/chao1UnmodifiedIdentifier.png
    type: File
  observed_otuslinkerprimersequence:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/observed_otusLinkerPrimerSequence.png
    type: File
  observed_otussampleid:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/observed_otusSampleID.png
    type: File
  rarefaction_plots:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/rarefaction_plots.html
    type: File
  chao1:
    outputBinding:
      glob: alpha_out/alpha_div_collated/chao1.txt
    type: File
  observed_otusbarcodesequence:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/observed_otusBarcodeSequence.png
    type: File
  observed_otusdescription:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/observed_otusDescription.png
    type: File
  shannon:
    outputBinding:
      glob: alpha_out/alpha_div_collated/shannon.txt
    type: File
  chao1description:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/chao1Description.png
    type: File
  observed_otus:
    outputBinding:
      glob: alpha_out/alpha_div_collated/observed_otus.txt
    type: File
  shannondescription:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/shannonDescription.png
    type: File
  alpha_out_log:
    outputBinding:
      glob: alpha_out/log_*.txt
    type: File
  chao1sampleid:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/chao1SampleID.png
    type: File
  PLACEHOLDER_output_alpha_diversity:
    type: stdout
  shannonlocalisation:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/shannonLocalisation.png
    type: File
  shannonbarcodesequence:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/shannonBarcodeSequence.png
    type: File
  shannonsampleid:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/shannonSampleID.png
    type: File
  alpha_out_directory:
    outputBinding:
      glob: alpha_out
    type: Directory
  shannonunmodifiedidentifier:
    outputBinding:
      glob: alpha_out/alpha_rarefaction_plots/average_plots/shannonUnmodifiedIdentifier.png
    type: File
stdout: $(inputs.PLACEHOLDER_output_alpha_diversity_outputFileName)
