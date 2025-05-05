
## 🧱 `server`

This is the **Ktor-based backend** for the OWL-E application. It defines the API surface, handles persistence, validates requests, and encodes business logic. It imports from the `common` module and builds out the complete server-side stack.

### 📦 Structure & Key Areas

```
server/
├── build.gradle.kts
├── src/main/kotlin/
│   ├── com/owle/server/
│   │   ├── api/                 # Route definitions for each API (e.g., WorkflowApi)
│   │   ├── data/                # DB integration, Exposed table definitions
│   │   ├── endpoint/            # Generic CRUD abstractions using sealed operations
│   │   ├── plugin/              # Ktor plugins (e.g., serialization, error handling)
│   │   ├── repository/          # Business logic layer interfacing with Exposed
│   │   ├── validation/          # Concrete validators & integration with API layer
│   │   ├── OwleServer.kt        # Main entrypoint to Ktor app
```

### 🧰 Core Components

#### 1\. **API Layer (`api/`)**

Each entity has a dedicated API route class (e.g., `WorkflowApi`), an implementation of `RoutedApi`, whose contract enables it to be registered through Ktor's `Routing`  at application start up.

which extends a generic base like `BaseEndpointImpl<ID: Any, T: BaseDto>`. These register routes under a path like `/api/workflow`.

They use:

-   `OwleApiCall<T>` to represent intent (read, create, delete, etc.)

-   `AsyncResult<T>` and `ApiResponse` as standard response envelopes

