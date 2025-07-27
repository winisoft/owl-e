#!/usr/bin/env bash
set -euo pipefail

FILE="commit_msg.txt"
MAX_LINE_LEN=72
MIN_SUMMARY_LEN=10
MAX_SUMMARY_LEN=75

# Fail if file missing or empty
if [[ ! -s "$FILE" ]]; then
  echo "❌ Commit message is empty or missing."
  exit 1
fi

mapfile -t lines < "$FILE"
summary="${lines[0]}"

# Strip conventional tags from beginning if present
if [[ "$summary" =~ ^(feat|fix|refactor|chore|test|style|docs):[[:space:]]* ]]; then
  echo "⚠️ Summary contains conventional tag. Stripping..."
  summary="$(sed -E 's/^(feat|fix|refactor|chore|test|style|docs):[[:space:]]*//' <<< "$summary")"
  lines[0]="$summary"
  printf "%s\n" "${lines[@]}" > "$FILE"
fi

# Check summary length
summary_len="${#summary}"
if (( summary_len < MIN_SUMMARY_LEN || summary_len > MAX_SUMMARY_LEN )); then
  echo "❌ Summary length invalid (${summary_len} characters). Must be 10–75."
  exit 1
fi

# Check each body line (if any) for 72-char width
for ((i = 1; i < ${#lines[@]}; i++)); do
  line="${lines[i]}"
  [[ -z "$line" ]] && continue
  line_len="${#line}"
  if (( line_len > MAX_LINE_LEN )); then
    echo "❌ Line $((i + 1)) exceeds ${MAX_LINE_LEN} characters:"
    echo "$line"
    exit 1
  fi
done

echo "✅ Commit message validated:"
printf "%s\n" "${lines[@]}"
