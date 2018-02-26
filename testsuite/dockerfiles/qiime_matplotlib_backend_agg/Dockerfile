FROM quay.io/biocontainers/qiime:1.9.1--np110py27_1

RUN cd /root && echo "backend : agg" > matplotlibrc && mkdir -p /var/spool/cwl && cd /var/spool/cwl && echo "backend : agg" > matplotlibrc && mkdir -p ~/.config/matplotlib && echo "backend : agg" > ~/.config/matplotlib/matplotlibrc
