#!/usr/bin/env bash
# One interpreter at a time: the introspector reads whatever Python it runs under, and selects its
# target from sys.version_info, so each environment collects exactly its own version.
cd "$(dirname "$0")"
set -a; . ./.env; set +a
export PYTHONIOENCODING=utf-8
for e in py3-14-7 py3-13-15 py3-12-13 py3-11-15 py3-10-20 py3-9-25 py3-8-20 py3-6-13; do
  P="/c/Users/wini/miniconda3/envs/$e/python.exe"
  [ -x "$P" ] || { echo "SKIP $e (no interpreter)"; continue; }
  start=$(date +%s)
  if timeout 3600 "$P" main.py > "/tmp/intro_$e.log" 2>&1; then
    printf "OK   %-12s %4ss  %s\n" "$e" "$(( $(date +%s) - start ))" \
      "$(grep -oE 'callables: [0-9]+ introspected, [0-9]+ skipped' "/tmp/intro_$e.log" | head -1)"
  else
    printf "FAIL %-12s %4ss  %s\n" "$e" "$(( $(date +%s) - start ))" \
      "$(grep -E '^[A-Za-z_.]+(Error|Exception):' "/tmp/intro_$e.log" | tail -1)"
  fi
done
