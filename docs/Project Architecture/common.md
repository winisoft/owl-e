# 🧱 `common`

This module is the shared heart of the OWL-E application, designed to be dependency-free from any platform-specific concerns (notably, no Java dependencies). It defines core types, serialization logic, validation scaffolding, and other model-level logic that flows bidirectionally across server and clients.

The `common` module defines core data types, serialization rules, and utility functions shared across all components of the Owl-E application. It is designed to be **pure Kotlin** (no JVM-specific APIs) to enable reuse across Kotlin/Multiplatform targets, including server, desktop, and web clients.

### 📦 Packages & Structure

```
common/
├── build.gradle.kts
├── src/commonMain/kotlin/
│   ├── com/owle/common/
│   │   ├── api/              # Shared API contracts (DTOs, response/result types)
│   │   ├── model/            # Core data types representing domain concepts
│   │   ├── validation/       # DTO validation interfaces and types
│   │   ├── util/             # Generic helpers, such as datetime conversion
```

> **Note:** This module is a **pure Kotlin Multiplatform (KMP)** module. It explicitly avoids JVM-only dependencies to ensure maximum portability.

### 🔄 Responsibilities

-   **Data Contracts:** DTOs used for serialization across the API boundary.
-   **Validation:** Declarative and structured validation interfaces, such as `DtoValidator<T>`, `ValidationResult`, `FieldValidation`, and `ConstraintViolation`.
