import json
from typing import Dict
from config import MAX_FILE_SIZE, OUTPUT_DIR
from config import OUTPUT_DIR

import os
from datetime import datetime, timezone

ERROR_LOG = "failed_prompts.jsonl"


def _get_output_file() -> str:
    """Find or create a writable JSON lines file under size threshold."""
    existing = sorted(
        [f for f in os.listdir(OUTPUT_DIR) if f.startswith("failures_") and f.endswith(".jsonl")]
    )
    for fname in existing:
        fpath = os.path.join(OUTPUT_DIR, fname)
        if os.path.getsize(fpath) < MAX_FILE_SIZE:
            return fpath
    # Create new one
    timestamp = datetime.now(timezone.utc).strftime("%Y%m%dT%H%M%SZ")
    new_path = os.path.join(OUTPUT_DIR, f"failures_{timestamp}.jsonl")
    return new_path


def persist_failed_response(distribution: str, version: str, payload: Dict):
    """Appends a failed payload to disk, rotating files as needed."""
    outfile = _get_output_file()
    record = {
        "distribution": distribution,
        "version": version,
        "timestamp": datetime.now(timezone.utc).isoformat(),
        "payload": payload,
    }
    with open(outfile, "a", encoding="utf-8") as f:
        f.write(json.dumps(record, ensure_ascii=False) + "\n")


def insert_or_log(cursor, insert_sql, values, prompt, response):
    try:
        cursor.execute(insert_sql, values)
        return True
    except Exception as e:
        with open(ERROR_LOG, "a") as f:
            json.dump({
                "timestamp": datetime.now().isoformat(),
                "prompt": prompt,
                "response": response,
                "error": str(e)
            }, f)
            f.write("\n")
        return False