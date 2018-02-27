---
cwlVersion: v1.0
class: CommandLineTool
baseCommand: beta_diversity_through_plots.py
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
- position: 6
  shellQuote: false
  valueFrom: -f -o beta_out
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
      position: 4
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
      position: 5
      prefix: '-O '
      separate: false
      shellQuote: false
    type: int
outputs:
  ui_bg_glas_gf_f_bxea_ib:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_glass_65_ffffff_1x400.png
    type: File
  favicon_eg:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/img/favicon.ico
    type: File
  spectrum_bi:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/spectrum.js
    type: File
  chosen_gf:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/chosen.jquery.min.js
    type: File
  ui_bg_highlight_soft_hf_c_bxba_dh:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_highlight-soft_75_cccccc_1x100.png
    type: File
  svgrenderer_fh:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/js/SVGRenderer.js
    type: File
  d_ci:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/d3.parcoords.css
    type: File
  jquery_ui_b_cj:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/jquery-ui-1.8.16.custom.css
    type: File
  jquery_b_f:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/jquery-1.6.2.min.js
    type: File
  ui_bg_glas_f_fbfje_bxea_eb:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_glass_55_fbf9ee_1x400.png
    type: File
  play_eh:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/img/play.png
    type: File
  unweighted_unifrac_pc_bad:
    outputBinding:
      glob: beta_out/unweighted_unifrac_pc.txt
    type: File
  ui_icons_ceidf_cfgxcea_e:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-icons_2e83ff_256x240.png
    type: File
  jquery_b_b:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/jquery-1.7.1.min.js
    type: File
  jquery_b_fe:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/jquery-1.6.2.min.js
    type: File
  reset_j:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/img/reset.png
    type: File
  emperor_gj:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/js/emperor.js
    type: File
  ui_hd:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/js/ui.js
    type: File
  thre_g:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/three.min.js
    type: File
  ui_icons_efefef_cfgxcea_id:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-icons_454545_256x240.png
    type: File
  ui_bg_flat_hf_f_eaxba_jb:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_flat_75_ffffff_40x100.png
    type: File
  colorconverter_ba:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/js/ColorConverter.js
    type: File
  draw_c:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/js/draw.js
    type: File
  ui_icons_i_cfgxcea_ed:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-icons_888888_256x240.png
    type: File
  ui_bg_glas_hf_dadada_bxea_ie:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_glass_75_dadada_1x400.png
    type: File
  animate_cf:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/js/animate.js
    type: File
  detector_h:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/js/Detector.js
    type: File
  ui_icons_c_cfgxcea_dg:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-icons_222222_256x240.png
    type: File
  ui_bg_glas_hf_egegeg_bxea_di:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_glass_75_e6e6e6_1x400.png
    type: File
  jquery_ui_b_d:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/jquery-ui-1.8.17.custom.min.js
    type: File
  chroma_gi:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/chroma.min.js
    type: File
  colorpicker_hj:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/colorPicker.css
    type: File
  beta_out_directory:
    outputBinding:
      glob: beta_out
    type: Directory
  jquery_be:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/jquery.colorPicker.js
    type: File
  d_f:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/d3.parcoords.js
    type: File
  d_e:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/d3.v3.min.js
    type: File
  ui_ce:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/js/ui.js
    type: File
  jquery_uic_ef:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/jquery-ui2.css
    type: File
  d_h:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/d3.parcoords.css
    type: File
  d_g:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/d3.parcoords.js
    type: File
  filesaver_gb:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/FileSaver.min.js
    type: File
  d_fd:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/d3.v3.min.js
    type: File
  spectrum_hg:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/spectrum.css
    type: File
  orbitcontrols_j:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/js/OrbitControls.js
    type: File
  ui_bg_flat_a_a_eaxba_ij:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_flat_0_aaaaaa_40x100.png
    type: File
  emperor_ei:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/img/emperor.png
    type: File
  pause_ej:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/img/pause.png
    type: File
  jquery_ui_b_fc:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/jquery-ui-1.8.17.custom.min.js
    type: File
  ui_bg_glas_gf_f_bxea_dc:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_glass_65_ffffff_1x400.png
    type: File
  trajectory_ha:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/js/trajectory.js
    type: File
  threx_bd:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/THREEx.screenshot.js
    type: File
  index_ba:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/index.html
    type: File
  jquery_b_ga:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/jquery-1.7.1.min.js
    type: File
  util_hc:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/js/util.js
    type: File
  spectrum_gh:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/spectrum.js
    type: File
  emperor_jh:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/img/emperor.png
    type: File
  orbitcontrols_fi:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/js/OrbitControls.js
    type: File
  beta_out_log:
    outputBinding:
      glob: beta_out/log_*.txt
    type: File
  ui_icons_cda_cfgxcea_dj:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-icons_cd0a0a_256x240.png
    type: File
  weighted_unifrac_pc_bac:
    outputBinding:
      glob: beta_out/weighted_unifrac_pc.txt
    type: File
  ui_bg_glas_jf_fefbec_bxea_ic:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_glass_95_fef1ec_1x400.png
    type: File
  ui_icons_i_cfgxcea_jc:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-icons_888888_256x240.png
    type: File
  util_cd:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/js/util.js
    type: File
  thre_bh:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/three.min.js
    type: File
  underscore_min_ge:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/underscore-min.js
    type: File
  ui_icons_c_cfgxcea_if:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-icons_222222_256x240.png
    type: File
  ui_bg_highlight_soft_hf_c_bxba_ig:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_highlight-soft_75_cccccc_1x100.png
    type: File
  emperor_cg:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/css/emperor.css
    type: File
  ui_bg_glas_hf_egegeg_bxea_ih:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_glass_75_e6e6e6_1x400.png
    type: File
  svgrenderer_i:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/js/SVGRenderer.js
    type: File
  colorconverter_fj:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/js/ColorConverter.js
    type: File
  jquery_ui_b_hi:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/jquery-ui-1.8.16.custom.css
    type: File
  emperor_ca:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/js/emperor.js
    type: File
  animate_he:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/js/animate.js
    type: File
  detector_fg:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/js/Detector.js
    type: File
  chosen_db:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/chosen.min.css
    type: File
  filesaver_bc:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/FileSaver.min.js
    type: File
  ui_icons_efefef_cfgxcea_de:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-icons_454545_256x240.png
    type: File
  ui_bg_flat_hf_f_eaxba_ec:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_flat_75_ffffff_40x100.png
    type: File
  reset_fa:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/img/reset.png
    type: File
  play_jg:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/img/play.png
    type: File
  chroma_bj:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/chroma.min.js
    type: File
  chosen_ia:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/chosen.min.css
    type: File
  pause_ji:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/img/pause.png
    type: File
  underscore_min_bf:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/underscore-min.js
    type: File
  threx_gc:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/THREEx.screenshot.js
    type: File
  emperor_hf:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/css/emperor.css
    type: File
  trajectory_cb:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/js/trajectory.js
    type: File
  ui_bg_glas_hf_dadada_bxea_df:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_glass_75_dadada_1x400.png
    type: File
  jquery_gd:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/jquery.colorPicker.js
    type: File
  draw_hb:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/emperor/js/draw.js
    type: File
  colorpicker_da:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/colorPicker.css
    type: File
  ui_bg_glas_jf_fefbec_bxea_d:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_glass_95_fef1ec_1x400.png
    type: File
  ui_icons_ceidf_cfgxcea_jd:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-icons_2e83ff_256x240.png
    type: File
  spectrum_ch:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/spectrum.css
    type: File
  ui_bg_flat_a_a_eaxba_ea:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_flat_0_aaaaaa_40x100.png
    type: File
  favicon_jf:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/img/favicon.ico
    type: File
  PLACEHOLDER_output_beta_diversity:
    type: stdout
  jquery_uic_je:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/jquery-ui2.css
    type: File
  chosen_bg:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/emperor_required_resources/js/chosen.jquery.min.js
    type: File
  unweighted_unifrac_dm_bab:
    outputBinding:
      glob: beta_out/unweighted_unifrac_dm.txt
    type: File
  ui_icons_cda_cfgxcea_i:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-icons_cd0a0a_256x240.png
    type: File
  index_fb:
    outputBinding:
      glob: beta_out/unweighted_unifrac_emperor_pcoa_plot/index.html
    type: File
  ui_bg_glas_f_fbfje_bxea_ja:
    outputBinding:
      glob: beta_out/weighted_unifrac_emperor_pcoa_plot/emperor_required_resources/css/images/ui-bg_glass_55_fbf9ee_1x400.png
    type: File
  weighted_unifrac_dm_bae:
    outputBinding:
      glob: beta_out/weighted_unifrac_dm.txt
    type: File
stdout: $(inputs.PLACEHOLDER_output_beta_diversity_outputFileName)
