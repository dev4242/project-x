#!/bin/bash
THIS_DIR="$(dirname $(readlink -f ${BASH_SOURCE[0]}))"
export PATH="${THIS_DIR}/bin":"$PATH"
