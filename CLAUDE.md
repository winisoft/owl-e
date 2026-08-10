# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Owl-E is a sophisticated workflow automation and agentic AI software platform designed to bridge the gap between practical automation needs and advanced AI safety considerations. The project is a multi-platform Kotlin application using Compose Multiplatform for the UI and Ktor for the backend, with several supporting Python utilities for data processing and type introspection.

## Read First — Current State (updated 2026-08-10)

- **[docs/REPO-STATE.md](docs/REPO-STATE.md)** — ground-truth inventory: what exists, what drifted, and the naming map between older prose docs and the actual schema. Read it before trusting anything below or in `docs/Owl-E.md`.
- **[docs/EXECUTION-ENVIRONMENT.md](docs/EXECUTION-ENVIRONMENT.md)** — the active design focus: generated Python runner + containerized, mock-instrumented execution ("access points").
- Schema truth is split: `docs/create.sql` (refreshed 2026-08-10) is the **final dump of the now-decommissioned MySQL database** (there is currently no live DB; row exports pending recovery), while the Exposed entity layer (`server/src/main/kotlin/com/wini/owle/entity/**`) is where the code is. They drifted in both directions — reconciliation table in [docs/REPO-STATE.md](docs/REPO-STATE.md) §2.
- `docs/composeApp/` (if still present) is an accidental byte-identical copy of `composeApp/`; the tracked module was restored 2026-08-10 and the copy is safe to delete.

## Project Structure

This is a Gradle multi-module project with the following key modules:

### Core Modules
- **`composeApp/`** - Compose Multiplatform client (desktop & web) 
- **`server/`** - Ktor-based backend API with database persistence
- **`common/`** - Shared types, DTOs, validation interfaces, and utilities
- **`type-schema/`** - Kotlin Symbol Processing (KSP) module for schema validation

### Supporting Modules  
- **`jep-utils/`** - Java Embedded Python utilities
- **`pyside/`** - Python data processing utilities for PyPI metadata and AI prompts
- **`type_introspector/`** - Python type introspection system for CPython analysis

## Development Commands

### Build and Run
```bash
# Build entire project
./gradlew build

# Run desktop application
./gradlew :composeApp:run

# Run server
./gradlew :server:run
```

### Testing
```bash
# Run all tests
./gradlew test

# Run unit tests only (server module)
./gradlew :server:unitTest

# Run integration tests only (server module)  
./gradlew :server:integrationTest
```

### Development Tasks
```bash
# Clean build
./gradlew clean

# Generate documentation
./gradlew dokka
```

## Architecture

### High-Level Architecture
The application follows a layered architecture with clear separation between UI, API, and data layers:

1. **Frontend Layer**: Compose Multiplatform UI (desktop/web)
2. **API Layer**: Ktor server with REST endpoints
3. **Business Logic**: Workflow orchestration and validation
4. **Data Layer**: MySQL database with Exposed ORM
5. **Supporting Services**: Python utilities for data processing

### Core Domain Concepts

#### Workflows
- Central data model for user-facing activity
- Hierarchical structure of phases and steps
- Support for interconnection via workflow junctions
- Testable with defined inputs and outputs

#### Steps  
- Fine-grained units within workflows
- Associated with specific tools/functions
- Execution based on display_order and default_next_step_id
- Runtime validation through inspectors

#### Data Management
- Typed data items with storage capacity controls
- Assignment rules for data transformation
- Consumer injections for data sharing between workflows
- Type system validation through type-schema module

#### Execution Environment
- Controlled execution sandbox
- Runtime validation and error handling
- Simulation capabilities for safe testing
- Comprehensive audit trails

### Key Dependencies
- **Kotlin**: 2.1.0 (multiplatform)
- **Compose Multiplatform**: 1.7.3
- **Ktor**: 2.3.13 (server)
- **Exposed**: 1.0.0-beta-2 (ORM)
- **Koin**: 4.1.0 (dependency injection)
- **MySQL**: Database persistence
- **Python 3.12+**: For type introspection utilities

## Database Configuration

The application uses MySQL for persistence. Key configuration:
- **Development DB**: `owle_dev`
- **Test DB**: `owle_test`
- Connection details configured per module

## Python Utilities

### Type Introspector (`type_introspector/`)
Python system for analyzing CPython source code and building type system database:
```bash
cd type_introspector
pip install -r requirements.txt
python main.py
```

### PySide Utilities (`pyside/`)  
Data processing utilities for PyPI metadata and AI-generated sample data:
```bash
cd pyside
pip install -r requirements.txt
python main.py
```

## Key Development Patterns

### Module Organization
- Each module follows standard Gradle project structure
- Shared code in `common/` module
- KSP-generated validation logic in `type-schema/`
- Test separation: unit tests in `src/test/unit/`, integration in `src/test/integration/`

### Data Flow
- Frontend communicates with backend via REST API
- Backend uses Exposed ORM for database operations
- Type validation generated at compile-time via KSP
- Data transformation handled through assignment rules

### Safety and Constraints
- Controlled execution environment for workflows
- Tool constraints through approved tool sets
- Data integrity through storage capacity controls
- Workflow integrity via unique constraints and validation

## Testing Strategy

- **Unit Tests**: Fast, isolated tests for business logic
- **Integration Tests**: Database and API integration testing  
- **Workflow Validation**: Testing of complete workflow execution
- **Type Safety**: Compile-time validation through KSP

## Important Notes

- Main application entry point: `com.wini.owle.MainKt`
- Package structure: `com.wini.owle.*`
- JVM target: Java 17
- The system emphasizes modularity, safety, and flexibility
- Database credentials may be hardcoded in development (see individual module docs)
- Python utilities require separate dependency management