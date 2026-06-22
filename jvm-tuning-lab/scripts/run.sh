#!/usr/bin/env bash
set -euo pipefail

export JAVA_HOME="${JAVA_HOME:-/opt/homebrew/opt/openjdk@21}"
export PATH="$JAVA_HOME/bin:$PATH"

java ${JVM_OPTS:-} -jar target/jvm-tuning-lab-1.0.0.jar "$@"
