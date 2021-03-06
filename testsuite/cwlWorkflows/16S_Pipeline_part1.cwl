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
  qiime_metadata_mappingFile: File
  qiime_parameter_file: File
  qiime_reference_sequences: ["null", File]
  qiime_sample_ids: string
  qiime_demultiplex_output_directory: string
  qiime_barcode_type: string
  qiime_phred_offset: int
  qiime_phred_quality_threshold: int
  qiime_parallel: boolean
  qiime_otu_picking_method: string
  qiime_skip_step_four: boolean
  qiime_jobs_to_start: int
  qiime_verbose: boolean
  qiime_biom_output_file_name: string




outputs:
  representative_tree: 
    type: File
    outputSource: qiime_pick_open_reference_otus/rep_set_tre
  otu_table_failures_biom:
    type: File
    outputSource: qiime_pick_open_reference_otus/otu_table_mc2_w_tax_no_pynast_failures_biom
  otu_table_failures_biom_metadata:
    type: File
    outputSource: qiime_biom_add_metadata/output_biom
  alpha_out:
    type: File
    outputSource: alpha_rarefaction/alpha_out

      - observed_otusunmodifiedidentifier
      - chao1linkerprimersequence
      - chao1localisation
      - observed_otusdescription
      - shannon
      - chao1description
      - chao1barcodesequence
      - log_20180216210531
      - shannonlinkerprimersequence
      - observed_otus
      - shannondescription
      - observed_otuslocalisation
      - chao1unmodifiedidentifier
      - observed_otuslinkerprimersequence
      - chao1sampleid
      - PLACEHOLDER_output_alpha_diversity
      - shannonlocalisation
      - observed_otussampleid
      - shannonbarcodesequence
      - rarefaction_plots
      - shannonsampleid
      - shannonunmodifiedidentifier
      - chao1
      - observed_otusbarcodesequence
    
    
    



steps:
  batch_flash:
    run: batch_flash.cwl
    in:
      flash_use_interleaved: flash_use_interleaved
      flash_density: flash_density
      flash_use_outies: flash_use_outies
      flash_min_size: flash_min_size
      flash_interleavedFASTQ: flash_interleavedFASTQ
      flash_max_size: flash_max_size
    out:
      - batch_extendedFrags
      - batch_histogram
      - batch_hist
      - batch_flash_stdout
      - batch_notCombined_1
      - batch_notCombined_2
#    scatter:
#      - flash_interleavedFASTQ
#    scatterMethod: dotproduct
  
  
  qiime_demultiplex:
    run: ../cwlTools/function_qiime_demultiplex.cwl
    in:
      input_merged_reads: batch_flash/batch_extendedFrags
      phred_quality_threshold: qiime_phred_quality_threshold
      input_metadata_mapping_files: qiime_metadata_mappingFile
      sample_id: qiime_sample_ids
      output_demultiplexed_directory_outputFileName: qiime_demultiplex_output_directory
      barcode_type: qiime_barcode_type
      phred_offset: qiime_phred_offset
    out:
      - outputfile_demultiplexed_seqs
      - outputfile_demultiplex_split_library_log
      - outputfile_qiime_demultiplex_histograms
  
  
  qiime_pick_open_reference_otus:
    run: ../cwlTools/function_qiime_pick_open_reference_otus.cwl
    in:
      input_splitted_libraries: qiime_demultiplex/outputfile_demultiplexed_seqs
      parallel: qiime_parallel
      otu_picking_method: qiime_otu_picking_method
      input_reference_sequences: qiime_reference_sequences
      suppress_step_four: qiime_skip_step_four
      jobs_to_start: qiime_jobs_to_start
      verbose: qiime_verbose
      input_parameter_file: qiime_parameter_file
    out: 
      - rep_set_tre
      - otu_table_mc2_w_tax_no_pynast_failures_biom
  
  
  qiime_biom_add_metadata:
    run: ../cwlTools/function_qiime_biom_add_metadata.cwl
    in:
      input_metadata_mapping_files: qiime_metadata_mappingFile
      input_out_table_mc2_w_tax_no_pynast_failures_biom: qiime_pick_open_reference_otus/otu_table_mc2_w_tax_no_pynast_failures_biom
      output_biom_outputFileName: qiime_biom_output_file_name
    out:
      - output_biom
  
  
  alpha_diversity:
    run: ../cwlTools/function_qiime_alpha_diversity.cwl
    in:
      input_otu_table: qiime_biom_add_metadata/output_biom
      parallel: qiime_parallel
      input_mapping: qiime_metadata_mappingFile
      input_tree_file: qiime_pick_open_reference_otus/rep_set_tre
      jobs_to_start: qiime_jobs_to_start
      input_parameter_file: qiime_parameter_file
    out:
      - observed_otusunmodifiedidentifier
      - chao1linkerprimersequence
      - chao1localisation
      - observed_otusdescription
      - shannon
      - chao1description
      - chao1barcodesequence
      - log_20180216210531
      - shannonlinkerprimersequence
      - observed_otus
      - shannondescription
      - observed_otuslocalisation
      - chao1unmodifiedidentifier
      - observed_otuslinkerprimersequence
      - chao1sampleid
      - PLACEHOLDER_output_alpha_diversity
      - shannonlocalisation
      - observed_otussampleid
      - shannonbarcodesequence
      - rarefaction_plots
      - shannonsampleid
      - shannonunmodifiedidentifier
      - chao1
      - observed_otusbarcodesequence

    
