FROM ubuntu:17.04

ADD ./FLASH2-master /progs/flash/

ENV PATH="/progs/flash:${PATH}"

RUN apt-get update && \
    apt-get install -y --no-install-recommends gcc make && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /progs/flash

RUN cd /progs/flash/ && \
    make

RUN ./flash2 --version


