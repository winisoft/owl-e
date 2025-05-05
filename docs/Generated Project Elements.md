# Owl-e API Documentation System

## Architecture Overview

This system uses KSP (Kotlin Symbol Processing) to automatically generate comprehensive API documentation by analyzing the existing codebase structure rather than requiring extensive manual annotation.

### Core Components

1. **ApiDocProcessor** - Main processor that orchestrates documentation extraction from:
   - API endpoints (BaseEndpointImpl subclasses)
   - DTO models (BaseDto implementations)
   - Error types (OwleError implementations)

2. **RouteExtractor** - Specialized utility that parses Ktor routing DSL in register() methods to extract:
   - HTTP methods (GET, POST, PUT, DELETE)
   - Path patterns (/api/resource/{id})
   - Operation types (mapped to OwleApiCall constants)

3. **ModelSchemaProcessor** - Processes DTO model classes to extract:
   - Properties and their types
   - Validation constraints from annotations
   - Documentation from KDoc comments

### Documentation Model

The documentation is represented as strongly-typed Kotlin data classes in `com.wini.owle.content.doc`:

- **ApiEndpointDoc** - Documents an API endpoint class
- **ApiRouteDoc** - Documents a specific API route
- **ApiOperationDoc** - Documents a specific HTTP method on a route
- **DtoModelDoc** - Documents a DTO model class
- **OwleErrorDoc** - Documents an error type

### Output Formats

The system generates:

1. **OpenAPI Specification** - JSON file conforming to OpenAPI 3.0.0 standard
2. **Markdown Documentation** - Per-endpoint documentation files
3. **Model Documentation** - Documentation for DTO models and their constraints

## Design Principles

1. **Single Source of Truth** - Documentation is derived directly from code
2. **Minimal Annotation** - Only things that can't be inferred need annotations
3. **Type Safety** - All documentation models are type-safe Kotlin classes
4. **Convention over Configuration** - Leverages existing code structure

## KSP Implementation Notes

### Finding Class Implementations

Since KSP doesn't provide a direct `getSymbolsWithSupertype` function, we use a pattern of:

```kotlin
resolver
    .getAllFiles()
    .flatMap { it.declarations }
    .filterIsInstance<KSClassDeclaration>()
    .filter { classDecl ->
        classDecl.superTypes
            .any { it.resolve().declaration.qualifiedName?.asString() == targetTypeName }
    }
```

### Accessing Function Source

Working with function source code requires careful handling:

```kotlin
val functionSource = try {
    registerFunction.containingFile?.toString() ?: return emptyList()
} catch (e: Exception) {
    logger.error("Failed to access source code")
    return emptyList()
}
```

### Extracting Routes from Ktor DSL

The routing DSL is parsed using regex patterns to identify common Ktor patterns:

```kotlin
val httpMethodPattern = "(get|post|put|delete|patch)\\(\\s*\"([^\"]+)\"\\s*\\)\\s*\\{[\\s\\S]*?call\\.execute\\(\\s*OwleApiCall\\.(\\w+)\\s*\\)"
```

## Future Enhancements

1. Full AST traversal for more accurate route extraction
2. Support for security schemes and authentication
3. Client SDK generation
4. Integration with test generation
