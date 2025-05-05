## 🧬 `type-schema`

A Kotlin Symbol Processing (KSP) module responsible for analyzing, transforming, and emitting structured metadata about the application's model types.

___
### 🎯 Purpose

This module exists to **bridge the gap between code and documentation**. It performs static analysis of project model classes (primarily DTOs and validators) and emits:

-   **Validation rules** for each type, in a form usable by:
    -   The server (for request validation)
    -   Clients (for pre-validation / UI hints)
    -   Documentation tools (to describe what fields are required and why)

-   **API schema documentation**, including:
    -   Serializable field definitions
    -   Value constraints (e.g., max length, allowed patterns)
    -   Descriptions of types and relationships

-   **Error code catalog**, linking every user-facing error to:
    -   A stable identifier
    -   A category or domain (e.g., `validation`, `auth`, `internal`)
    -   Descriptive metadata for public display

___

### ⚙️ Key Technologies

| Component                      | Role                                                                |
| ------------------------------ | ------------------------------------------------------------------- |
| **KSP**                        | Annotation processor reading Kotlin source metadata at compile time |
| **KotlinPoet**                 | Code generation backend (planned)                                   |
| **JSON/YAML Output** (planned) | Structured outputs for schema and error catalog                     |
|                                |                                                                     |

🏗️ Planned Architecture

```
flowchart TD
  A[Kotlin DTOs & Validators]
  A --> B[KSP Visitor in type-schema]
  B --> C[Structured Type Metadata]
  C --> D[Generated Kotlin Code]
  C --> E[JSON Schema Docs]
  C --> F[Error Catalog]

  D --> G[Used by server/client at compile time]
  E --> H[Served as part of public documentation]
  F --> H
```

### 📌 Notes for Future Development
- All validation logic should be declared **structurally** in DTO-specific validators using a formal syntax.
- The KSP processor will walk `FieldValidation`, `ConstraintViolation`, and DTO/Validator pairs to derive a complete set of validation constraints.
- Rich metadata may be added via annotations or doc comments.
- The generated documentation should align with OpenAPI but avoid redundancy — the `type-schema` module is the source of truth.