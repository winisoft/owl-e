# Owle API Documentation System

This document explains how the automated API documentation system works in the Owle project.

## Overview

The API documentation system uses Kotlin Symbol Processing (KSP) to analyze annotated interfaces and generate comprehensive OpenAPI Specification (OAS) documentation. This approach allows for source-code-driven documentation that stays in sync with the actual API implementation.

## Key Components

1. **Annotations**: A set of annotations in the `com.wini.owle.schema` package that define API endpoints, routes, and operations
2. **Interfaces**: API interfaces that use these annotations to describe endpoints
3. **KSP Processor**: A processor that reads these annotations at compile time
4. **Output**: Generated OpenAPI JSON specification

## How It Works

### 1. API Interface Definition

Create an interface that describes your API endpoint:

```kotlin
@OwleApiSpec(
    basePath = "/api/user",
    dtoType = User::class,
    description = "API for managing user accounts",
    tags = ["user"])
interface IUserApi: IOwleApi {
    // The interface methods are annotated with specific HTTP method annotations
    @OwleApiGetMany(
        summary = "Get all users",
        description = "Returns a list of all users")
    override suspend fun getMany(block: suspend () -> Unit) { super.getMany(block) }
    
    // Additional methods...
}
```

### 2. Implementation

Implement the interface in your server code:

```kotlin
class UserApi(
    dll: DLL<Long, User>,
    validator: DtoValidator<User>
): LongIdEndpointImpl<User>(
    dll = dll,
    serializer = User.serializer(),
    validator = validator
), IUserApi {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/user") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            // Additional routes...
        }
    }
}
```

### 3. API Documentation Processing

The KSP processor analyzes all interfaces with `@OwleApiSpec` annotations at compile time and:

1. Extracts metadata (base path, description, tags)
2. Processes method annotations to document routes
3. Examines referenced DTO classes to document data models
4. Generates OpenAPI specification documents

## Available Annotations

### Class-Level Annotations

- `@OwleApiSpec`: Defines an API endpoint

### Method-Level Annotations

- `@OwleApiGetMany`: GET operation for retrieving multiple resources
- `@OwleApiGetOne`: GET operation for retrieving a single resource
- `@OwleApiPostMany`: POST operation for creating multiple resources
- `@OwleApiPostOne`: POST operation for creating a single resource
- `@OwleApiPutMany`: PUT operation for updating multiple resources
- `@OwleApiPutOne`: PUT operation for updating a single resource
- `@OwleApiDeleteMany`: DELETE operation for deleting multiple resources
- `@OwleApiDeleteOne`: DELETE operation for deleting a single resource

## Benefits

This approach offers several advantages:

1. **Single Source of Truth**: API documentation derives directly from code
2. **Type Safety**: Uses Kotlin's type system to ensure accuracy
3. **Minimal Boilerplate**: Simple annotations convey rich metadata
4. **Automatic Updates**: Documentation stays synchronized with code changes
5. **Consistent Standards**: Enforces consistent API patterns

## Building and Viewing Documentation

To generate the API documentation:

1. Build the project with KSP enabled
2. Find the generated OpenAPI specification at `build/generated/ksp/main/resources/openapi.json`
3. Use tools like Swagger UI or Redoc to view the documentation 