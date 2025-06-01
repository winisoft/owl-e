# PySide -- Owl-E's Python Companion

A Python application that performs a number of data processing tasks that are useful groundwork for the Owl-E Agentic Workflow Framework.

In other words: **IN-HOUSE UTILITY ONLY** -- general data processing helper script.

Basically for now I'm just working out a few things but holding onto a lot of code that will probably come in handy later as database versions increment.

So -- it's by design that the main.py function just has whichever 'conceptual process root function' is appropriate to the occasion.

## Overview

A few of the key aspects that may or may not be presently configured to run:
- Fetching package metadata from PyPI
- Automate prompts to an AI LLM running locally for meaningful sample data generation
- Introspect type-related data from the current environment and insert representative data elements
  
Take a look in the `docs` folder for more info.

## Configuration

The application can be configured using environment variables or a `.env` file:

- `DB_HOST`: Database host (default: localhost)
- `DB_USER`: Database username
- `DB_PASSWORD`: Database password
- `DB_NAME`: Database name (default: owle_test)
- `INPUT_DIR`: Directory for input files
- `OUTPUT_DIR`: Directory for output files
- `LOG_DIR`: Directory for log files
- `LOG_LEVEL`: Logging level (DEBUG, INFO, WARNING, ERROR, CRITICAL)
- `COMMIT_EVERY`: How often to commit to the database (default: 500)
- `PYPI_REQUEST_TIMEOUT`: Timeout for PyPI requests in seconds (default: 10)
- `MAX_RETRIES`: Maximum number of retries for failed requests (default: 3)