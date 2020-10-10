#!/bin/bash

set -eu

THIS_DIR="$(dirname $(readlink -f ${BASH_SOURCE[0]}))"

cd "${THIS_DIR}"
echo "## Running tests for $(dirname "${THIS_DIR}")"

../source/doit.sh
