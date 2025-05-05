## 💻 `composeApp`

This Gradle project hosts the **client-side UI applications**, all built using **JetBrains Compose Multiplatform (JBC)**. These clients share code where possible but target different platforms.

### 📦 Structure


```
compose-app/
├── build.gradle.kts
├── shared/                  # Shared UI logic and DTOs (commonMain)
├── desktop/                 # Desktop client (primary target)
├── web/                     # Experimental JS/Browser client
├── androidApp/              # Placeholder or future Android target
```

### 📲 Clients Overview

| Client | Status | Notes |
| --- | --- | --- |
| **desktop** | 🟢 Primary | Built using Compose Desktop. First to be deployed. |
| **web** | 🟡 Experimental | Compiles to JS via Kotlin/WASM. Low priority for now. |
| **androidApp** | ⛔ Inactive | Stub for future development. No active deployment. |

Each of these uses the `shared` module for most logic. Platform-specific modules handle:
-   App startup & lifecycle
-   Platform integrations (e.g., file system, windowing)
-   Packaging/distribution

### 🧩 `shared/` Module Details

This is the core of the Compose UI logic:
-   Declarative UI using Jetpack Compose-style components
-   Uses DTOs from the `common` module for request/response shapes
-   Makes HTTP requests to the server (e.g., via Ktor Client)
-   UI state modeled via immutable state flows and viewmodels

___

### 🚀 Deployment Considerations

For now, the first release will likely deploy:
-   The `server` component (Ktor backend)
-   The `desktop` client (Compose Desktop app)

These two can operate as a coupled system, with the desktop client pointing to the locally or remotely hosted backend.

Longer-term, the goal may be to:
-   Host web client as a static bundle alongside the backend
-   Package all three clients for multi-platform release
-   Keep all modules integrated within a single monorepo and CI/CD pipeline