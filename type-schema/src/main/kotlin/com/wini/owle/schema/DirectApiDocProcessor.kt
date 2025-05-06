package com.wini.owle.schema

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.wini.owle.content.doc.*
import com.wini.owle.ksp.OwleApiEndpoint
import com.wini.owle.ksp.OwleModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.io.File

///**
// * A simplified direct processor that extracts API documentation from the server module.
// *
// * This processor is designed to work with the new architecture where all models
// * and endpoints are defined in the server module, eliminating the need for
// * cross-module coordination.
// */
//class DirectApiDocProcessor(
//    private val codeGenerator: CodeGenerator,
//    private val logger: KSPLogger
//) : SymbolProcessor {
//
//    // Store collected endpoint documentation
//    private val endpoints = mutableListOf<ApiEndpointDoc>()
//
//    // Store collected model documentation
//    private val models = mutableMapOf<String, DtoModelDoc>()
//
//    // Store collected error documentation
//    private val errors = mutableMapOf<String, OwleErrorDoc>()
//
//    // Route extractor for parsing Ktor routing DSL
//    private val routeExtractor = RouteExtractor(logger)
//
//    override fun process(resolver: Resolver): List<KSAnnotated> {
//        try {
//            logger.info("Starting API documentation extraction with DirectApiDocProcessor")
//
//            // Process DTO models
//            processModels(resolver)
//
//            // Process errors
//            processErrors(resolver)
//
//            // Find all endpoint implementations and extract their routes
//            processEndpoints(resolver)
//
//            // Filter out abstract base classes
//            endpoints.removeIf { it.name == "BaseEndpointImpl" || it.name == "CompositeIdEndpointImpl" || it.name == "LongIdEndpointImpl" }
//
//            // Generate OpenAPI spec only if we found actual endpoints
//            if (endpoints.isNotEmpty()) {
//                generateOpenApiSpec()
//            } else {
//                logger.warn("No concrete API endpoints found. Skipping OpenAPI generation.")
//            }
//
//            // Generate individual endpoint docs
//            endpoints.forEach { endpoint ->
//                try {
//                    generateEndpointDoc(endpoint)
//                } catch (e: Exception) {
//                    logger.error("Error generating doc for endpoint ${endpoint.name}: ${e.message}")
//                }
//            }
//        } catch (e: Exception) {
//            logger.error("Error in API doc processing: ${e.message}")
//            e.printStackTrace()
//        }
//
//        logger.info("Completed API documentation extraction")
//        return emptyList()
//    }
//
//    /**
//     * Find all models (DTOs) in the codebase
//     */
//    private fun processModels(resolver: Resolver) {
//        logger.info("Processing DTO models")
//
//        // Find classes with @OwleModel annotation
//        val owleModelClasses = resolver
//            .getSymbolsWithAnnotation(OwleModel::class.qualifiedName!!)
//            .filterIsInstance<KSClassDeclaration>()
//
//        logger.info("Found ${owleModelClasses.count()} DTO models with @OwleModel annotation")
//
//        // Find all classes that implement BaseDto interface
//        val baseDtoName = "com.wini.owle.dto.BaseDto"
//        val baseDtoClasses = getSymbolsWithSupertype(resolver, baseDtoName)
//            .filterIsInstance<KSClassDeclaration>()
//
//        logger.info("Found ${baseDtoClasses.count()} DTO models implementing BaseDto")
//
//        // Process all models from both sources
//        val allModels = (owleModelClasses + baseDtoClasses).distinctBy { it.qualifiedName?.asString() }
//
//        allModels.forEach { modelClass ->
//            try {
//                val model = buildDtoDoc(modelClass)
//                models[modelClass.qualifiedName?.asString() ?: "unknown"] = model
//                logger.info("Processed model: ${model.name}")
//            } catch (e: Exception) {
//                logger.error("Error processing model ${modelClass.simpleName.asString()}: ${e.message}")
//            }
//        }
//    }
//
//    /**
//     * Build a DtoModelDoc for a class declaration
//     */
//    private fun buildDtoDoc(dtoClass: KSClassDeclaration): DtoModelDoc {
//        val name = dtoClass.simpleName.asString()
//
//        // Get description from @OwleModel annotation or KDoc
//        val description = dtoClass.annotations
//            .firstOrNull { it.shortName.asString() == "OwleModel" || it.annotationType.resolve().declaration.qualifiedName?.asString()?.endsWith(".OwleModel") == true }
//            ?.arguments
//            ?.firstOrNull { it.name?.asString() == "description" }
//            ?.value as? String
//            ?: dtoClass.docString?.trim()
//            ?: "No documentation available"
//
//        // Extract properties and their constraints
//        val properties = dtoClass.getAllProperties().map { prop ->
//            val propName = prop.simpleName.asString()
//            val type = prop.type.resolve().declaration.qualifiedName?.asString() ?: "Unknown"
//            val doc = prop.docString?.trim() ?: "No documentation available"
//
//            // Extract validation constraints from annotations
//            val constraints = prop.annotations.mapNotNull { annotation ->
//                val annClassName = annotation.annotationType.resolve().declaration.qualifiedName?.asString() ?: return@mapNotNull null
//
//                // Only process validation annotations (in com.wini.owle.ksp package)
//                if (!annClassName.startsWith("com.wini.owle.ksp")) return@mapNotNull null
//
//                // Extract error code and other attributes
//                val errorCode = annotation.arguments.firstOrNull { it.name?.asString() == "errorCode" }?.value as? String
//                    ?: annClassName.substringAfterLast('.')
//
//                val message = annotation.arguments.firstOrNull { it.name?.asString() == "message" }?.value as? String
//                    ?: "$propName must meet validation requirement $errorCode"
//
//                // Extract other attributes as a map
//                val attrMap: Map<String, String> = annotation.arguments.associate { arg ->
//                    (arg.name?.asString() ?: "") to (arg.value?.toString() ?: "")
//                }.filterKeys { it != "errorCode" && it != "message" }
//
//                OwleErrorDoc(
//                    errorCode = errorCode,
//                    message = message,
//                    attributes = attrMap
//                )
//            }.toList()
//
//            ModelPropertyDoc(
//                name = propName,
//                type = type,
//                description = doc,
//                constraints = constraints
//            )
//        }.toList()
//
//        return DtoModelDoc(
//            name = name,
//            description = description,
//            properties = properties
//        )
//    }
//
//    /**
//     * Process error classes in the codebase
//     */
//    private fun processErrors(resolver: Resolver) {
//        logger.info("Processing error types")
//
//        // Find all classes that implement OwleError
//        val owleErrorName = "com.wini.owle.error.OwleError"
//        val errorClasses = getSymbolsWithSupertype(resolver, owleErrorName)
//            .filterIsInstance<KSClassDeclaration>()
//
//        logger.info("Found ${errorClasses.count()} error classes")
//
//        errorClasses.forEach { errorClass ->
//            try {
//                // Skip abstract classes and interfaces
//                if (errorClass.isAbstract() || errorClass.classKind == ClassKind.INTERFACE) {
//                    return@forEach
//                }
//
//                val name = errorClass.simpleName.asString()
//
//                // Extract error code
//                val errorCodeProperty = errorClass.getAllProperties()
//                    .firstOrNull { it.simpleName.asString() == "errorCode" }
//
//                val errorCode = when {
//                    errorCodeProperty != null -> {
//                        // Try to extract constant value, fallback to class name pattern
//                        "ERR_${name.uppercase()}"
//                    }
//                    else -> name
//                }
//
//                // Extract message
//                val messageProperty = errorClass.getAllProperties()
//                    .firstOrNull { it.simpleName.asString() == "message" }
//
//                val message = when {
//                    messageProperty != null -> {
//                        // In a real implementation, we would try to extract the value
//                        // But for now we'll use a placeholder
//                        "Error message defined at runtime"
//                    }
//                    else -> "Unknown error"
//                }
//
//                // Get description from KDoc
//                val description = errorClass.docString?.trim() ?: "No description available"
//
//                val errorDoc = OwleErrorDoc(
//                    errorCode = errorCode,
//                    message = message,
//                    attributes = mapOf("description" to description)
//                )
//
//                errors[errorCode] = errorDoc
//                logger.info("Processed error: $errorCode")
//            } catch (e: Exception) {
//                logger.error("Error processing error class ${errorClass.simpleName.asString()}: ${e.message}")
//            }
//        }
//    }
//
//    /**
//     * Process all endpoint classes to extract API routes
//     */
//    private fun processEndpoints(resolver: Resolver) {
//        logger.info("Processing API endpoints")
//
//        // Find all classes that extend BaseEndpointImpl
//        val baseEndpointName = "com.wini.owle.api.BaseEndpointImpl"
//        val endpointClasses = getSymbolsWithSupertype(resolver, baseEndpointName)
//            .filterIsInstance<KSClassDeclaration>()
//
//        logger.info("Found ${endpointClasses.count()} API endpoint classes")
//
//        endpointClasses.forEach { endpointClass ->
//            try {
//                processEndpoint(endpointClass)
//            } catch (e: Exception) {
//                logger.error("Error processing endpoint ${endpointClass.simpleName.asString()}: ${e.message}")
//            }
//        }
//    }
//
//    /**
//     * Process a single endpoint class
//     */
//    private fun processEndpoint(endpointClass: KSClassDeclaration) {
//        val endpointName = endpointClass.simpleName.asString()
//        logger.info("Processing endpoint: $endpointName")
//
//        // Skip abstract classes and interfaces
//        if (endpointClass.isAbstract() || endpointClass.classKind == ClassKind.INTERFACE) {
//            logger.info("Skipping abstract class or interface: $endpointName")
//            return
//        }
//
//        // Extract endpoint documentation from KDoc or @OwleApiEndpoint annotation
//        val description = endpointClass.annotations
//            .firstOrNull { it.shortName.asString() == "OwleApiEndpoint" }
//            ?.arguments
//            ?.firstOrNull { it.name?.asString() == "description" }
//            ?.value as? String
//            ?: endpointClass.docString?.trim()
//            ?: "No documentation available"
//
//        // Extract tags from @OwleApiEndpoint annotation
//        val tags = endpointClass.annotations
//            .firstOrNull { it.shortName.asString() == "OwleApiEndpoint" }
//            ?.arguments
//            ?.firstOrNull { it.name?.asString() == "tags" }
//            ?.value as? ArrayList<*>
//            ?.filterIsInstance<String>()
//            ?: listOf(endpointName)
//
//        // Extract routes by analyzing register() method
//        val routes = extractRoutes(endpointClass)
//
//        logger.info("Found ${routes.size} routes for endpoint $endpointName")
//
//        // Create endpoint documentation
//        val endpointDoc = ApiEndpointDoc(
//            name = endpointName,
//            description = description,
//            tags = tags,
//            routes = routes
//        )
//
//        endpoints.add(endpointDoc)
//    }
//
//    /**
//     * Extract routes from an endpoint class's register() method
//     */
//    private fun extractRoutes(endpointClass: KSClassDeclaration): List<ApiRouteDoc> {
//        // Find the register() method
//        val registerMethod = endpointClass.getAllFunctions()
//            .firstOrNull { it.simpleName.asString() == "register" }
//            ?: return emptyList()
//
//        // Get method content for parsing
//        val functionBody = registerMethod.functionBody?.raw() ?: return emptyList()
//
//        // Use the route extractor to parse the Ktor DSL
//        return routeExtractor.extractRoutes(functionBody, endpointClass)
//            .map { routeInfo ->
//                // For each route, extract DTO type information
//                val dtoType = extractDtoType(routeInfo.operationType)
//
//                ApiRouteDoc(
//                    path = routeInfo.path,
//                    httpMethod = routeInfo.httpMethod,
//                    operation = ApiOperationDoc(
//                        type = routeInfo.operationType,
//                        description = routeInfo.description,
//                        requestType = dtoType,
//                        responseType = "com.wini.owle.api.ApiResponse",
//                        errorCodes = getErrorCodesForOperation(routeInfo.operationType)
//                    )
//                )
//            }
//    }
//
//    /**
//     * Extract DTO type for an operation type based on naming convention
//     */
//    private fun extractDtoType(operationType: String): String {
//        // Use naming convention: OperationType -> OperationTypeRequest
//        return "$operationType" + "Request"
//    }
//
//    /**
//     * Get relevant error codes for an operation type
//     */
//    private fun getErrorCodesForOperation(operationType: String): List<String> {
//        // Placeholder - in a real implementation this would analyze error handling
//        return errors.keys.toList()
//    }
//
//    /**
//     * Generate individual endpoint documentation as Markdown
//     */
//    private fun generateEndpointDoc(endpoint: ApiEndpointDoc) {
//        try {
//            // Find common prefix for routes to use as base path
//            val basePath = if (endpoint.routes.size > 1) {
//                findCommonPathPrefix(endpoint.routes.map { it.path })
//            } else {
//                endpoint.routes.firstOrNull()?.path ?: "/"
//            }
//
//            val markdownContent = buildString {
//                append("# ${endpoint.name}\n\n")
//                append("${endpoint.description}\n\n")
//
//                if (endpoint.tags.isNotEmpty()) {
//                    append("**Tags:** ${endpoint.tags.joinToString(", ")}\n\n")
//                }
//
//                append("## API Endpoints\n\n")
//
//                endpoint.routes.forEach { route ->
//                    append("### ${route.httpMethod.uppercase()} ${route.path}\n\n")
//
//                    val operation = route.operation
//                    append("Operation: `${operation.type}`\n\n")
//
//                    if (operation.description.isNotEmpty()) {
//                        append("${operation.description}\n\n")
//                    }
//
//                    // Document request
//                    append("#### Request\n\n")
//                    val requestType = operation.requestType
//                    val requestModel = models[requestType]
//
//                    if (requestModel != null) {
//                        append("**Type:** `$requestType`\n\n")
//                        append("${requestModel.description}\n\n")
//
//                        if (requestModel.properties.isNotEmpty()) {
//                            append("| Field | Type | Description | Constraints |\n")
//                            append("|-------|------|-------------|-------------|\n")
//
//                            requestModel.properties.forEach { prop ->
//                                val constraints = prop.constraints.joinToString("<br>") {
//                                    "${it.errorCode}: ${it.message}"
//                                }
//                                append("| ${prop.name} | `${prop.type}` | ${prop.description} | $constraints |\n")
//                            }
//
//                            append("\n")
//                        }
//                    } else {
//                        append("No detailed schema information available for request type `$requestType`\n\n")
//                    }
//
//                    // Document response
//                    append("#### Response\n\n")
//                    append("**Type:** `${operation.responseType}`\n\n")
//                    append("Standard API response containing the result data or error information.\n\n")
//
//                    // Document errors
//                    if (operation.errorCodes.isNotEmpty()) {
//                        append("#### Possible Errors\n\n")
//                        append("| Error Code | Message | Description |\n")
//                        append("|------------|---------|-------------|\n")
//
//                        operation.errorCodes.forEach { errorCode ->
//                            val error = errors[errorCode]
//                            if (error != null) {
//                                append("| $errorCode | ${error.message} | ${error.attributes["description"] ?: ""} |\n")
//                            } else {
//                                append("| $errorCode | Unknown error | No description available |\n")
//                            }
//                        }
//
//                        append("\n")
//                    }
//                }
//            }
//
//            // Save the markdown file
//            val outputDir = "api-docs/endpoints"
//            val fileName = endpoint.name.replace(Regex("[^a-zA-Z0-9]"), "_") + ".md"
//
//            val outputPath = File(outputDir)
//            if (!outputPath.exists()) {
//                outputPath.mkdirs()
//            }
//
//            File("$outputDir/$fileName").writeText(markdownContent)
//            logger.info("Generated documentation for endpoint ${endpoint.name} at $outputDir/$fileName")
//
//        } catch (e: Exception) {
//            logger.error("Error generating documentation for endpoint ${endpoint.name}: ${e.message}")
//        }
//    }
//
//    /**
//     * Find the common prefix for a list of paths
//     */
//    private fun findCommonPathPrefix(paths: List<String>): String {
//        if (paths.isEmpty()) return "/"
//        if (paths.size == 1) return paths[0]
//
//        val segments = paths.map { it.split("/") }
//        val minLength = segments.minOf { it.size }
//
//        var commonPrefixLength = 0
//        for (i in 0 until minLength) {
//            val segment = segments[0][i]
//            if (segments.all { it[i] == segment }) {
//                commonPrefixLength = i + 1
//            } else {
//                break
//            }
//        }
//
//        return segments[0].take(commonPrefixLength).joinToString("/", prefix = "/")
//    }
//
//    /**
//     * Generate OpenAPI specification JSON file
//     */
//    private fun generateOpenApiSpec() {
//        logger.info("Generating OpenAPI specification")
//
//        try {
//            val openApiSpec = OpenApiSpec(
//                openapi = "3.0.0",
//                info = JsonObject(buildJsonObject {
//                    put("title", "Owl-e API")
//                    put("description", "Owl-e API Documentation")
//                    put("version", "1.0.0")
//                }),
//                servers = JsonArray(buildJsonArray {
//                    add(buildJsonObject {
//                        put("url", "/api/v1")
//                        put("description", "Owl-e API v1")
//                    })
//                }),
//                paths = buildJsonObject {
//                    // Group endpoints by path to consolidate methods
//                    val pathMap = mutableMapOf<String, MutableList<Pair<String, ApiOperationDoc>>>()
//
//                    endpoints.forEach { endpoint ->
//                        endpoint.routes.forEach { route ->
//                            val path = route.path
//                            val operations = pathMap.getOrPut(path) { mutableListOf() }
//                            operations.add(Pair(route.httpMethod.lowercase(), route.operation))
//                        }
//                    }
//
//                    // Add each path
//                    pathMap.forEach { (path, operations) ->
//                        put(path, buildJsonObject {
//                            operations.forEach { (method, operation) ->
//                                put(method, buildJsonObject {
//                                    put("summary", operation.type)
//                                    put("description", operation.description.ifEmpty { "No description available" })
//
//                                    val relevantEndpoint = endpoints.first { endpoint ->
//                                        endpoint.routes.any { it.path == path && it.operation.type == operation.type }
//                                    }
//
//                                    // Add tags
//                                    put("tags", buildJsonArray {
//                                        relevantEndpoint.tags.forEach { add(it) }
//                                    })
//
//                                    // Add request body
//                                    val requestModel = models[operation.requestType]
//                                    if (requestModel != null) {
//                                        put("requestBody", buildJsonObject {
//                                            put("required", true)
//                                            put("content", buildJsonObject {
//                                                put("application/json", buildJsonObject {
//                                                    put("schema", buildJsonSchema(requestModel))
//                                                })
//                                            })
//                                        })
//                                    }
//
//                                    // Add responses
//                                    put("responses", buildJsonObject {
//                                        put("200", buildJsonObject {
//                                            put("description", "Successful operation")
//                                            put("content", buildJsonObject {
//                                                put("application/json", buildJsonObject {
//                                                    put("schema", buildJsonObject {
//                                                        put("type", "object")
//                                                        put("properties", buildJsonObject {
//                                                            put("success", buildJsonObject {
//                                                                put("type", "boolean")
//                                                                put("description", "Indicates if the operation was successful")
//                                                            })
//                                                            put("data", buildJsonObject {
//                                                                put("type", "object")
//                                                                put("description", "Response data for successful operations")
//                                                            })
//                                                            put("error", buildJsonObject {
//                                                                put("type", "object")
//                                                                put("description", "Error information for failed operations")
//                                                                put("properties", buildJsonObject {
//                                                                    put("code", buildJsonObject {
//                                                                        put("type", "string")
//                                                                        put("description", "Error code")
//                                                                    })
//                                                                    put("message", buildJsonObject {
//                                                                        put("type", "string")
//                                                                        put("description", "Error message")
//                                                                    })
//                                                                })
//                                                            })
//                                                        })
//                                                    })
//                                                })
//                                            })
//                                        })
//
//                                        // Add error responses
//                                        put("400", buildJsonObject {
//                                            put("description", "Bad request")
//                                        })
//                                        put("401", buildJsonObject {
//                                            put("description", "Unauthorized")
//                                        })
//                                        put("500", buildJsonObject {
//                                            put("description", "Server error")
//                                        })
//                                    })
//                                })
//                            }
//                        })
//                    }
//                },
//                components = buildJsonObject {
//                    put("schemas", buildJsonObject {
//                        // Add all model schemas
//                        models.forEach { (_, model) ->
//                            put(model.name, buildJsonSchema(model))
//                        }
//                    })
//
//                    put("securitySchemes", buildJsonObject {
//                        put("bearerAuth", buildJsonObject {
//                            put("type", "http")
//                            put("scheme", "bearer")
//                            put("bearerFormat", "JWT")
//                        })
//                    })
//                },
//                security = JsonArray(buildJsonArray {
//                    add(buildJsonObject {
//                        put("bearerAuth", buildJsonArray { })
//                    })
//                })
//            )
//
//            // Write OpenAPI spec to file
//            val outputDir = "api-docs"
//
//            val directory = File(outputDir)
//            if (!directory.exists()) {
//                directory.mkdirs()
//            }
//
//            val json = Json { prettyPrint = true; encodeDefaults = true }
//            val openApiJson = json.encodeToString(openApiSpec)
//
//            val outputPath = "$outputDir/openapi.json"
//            File(outputPath).writeText(openApiJson)
//
//            logger.info("OpenAPI specification generated at $outputPath")
//        } catch (e: Exception) {
//            logger.error("Error writing OpenAPI specification: ${e.message}")
//        }
//    }
//
//    /**
//     * Build a JSON Schema object for a model
//     */
//    private fun buildJsonSchema(model: DtoModelDoc): JsonObject {
//        return buildJsonObject {
//            put("type", "object")
//            put("description", model.description)
//
//            put("properties", buildJsonObject {
//                model.properties.forEach { prop ->
//                    put(prop.name, buildJsonObject {
//                        val type = getOpenApiType(prop.type)
//                        put("type", type)
//                        put("description", prop.description)
//
//                        // Add constraints as additional properties
//                        prop.constraints.forEach { constraint ->
//                            val attrs = constraint.attributes as? Map<String, String> ?: emptyMap()
//                            when (constraint.errorCode) {
//                                "MinLength" -> {
//                                    attrs["minLength"]?.toIntOrNull()?.let { put("minLength", it) }
//                                }
//                                "MaxLength" -> {
//                                    attrs["maxLength"]?.toIntOrNull()?.let { put("maxLength", it) }
//                                }
//                                "Pattern" -> {
//                                    attrs["pattern"]?.let { put("pattern", it) }
//                                }
//                                "Min" -> {
//                                    attrs["min"]?.toIntOrNull()?.let { put("minimum", it) }
//                                }
//                                "Max" -> {
//                                    attrs["max"]?.toIntOrNull()?.let { put("maximum", it) }
//                                }
//                                // Add more constraints as needed
//                            }
//                        }
//                    })
//                }
//            })
//
//            // Add required properties
//            val requiredProps = model.properties
//                .filter { it.constraints.any { c -> c.errorCode == "Required" } }
//                .map { it.name }
//
//            if (requiredProps.isNotEmpty()) {
//                put("required", buildJsonArray {
//                    requiredProps.forEach { add(it) }
//                })
//            }
//        }
//    }
//
//    /**
//     * Map Kotlin/Java types to OpenAPI types
//     */
//    private fun getOpenApiType(type: String): String {
//        return when {
//            type.contains("Int") || type.contains("Long") -> "integer"
//            type.contains("Float") || type.contains("Double") -> "number"
//            type.contains("Boolean") -> "boolean"
//            type.contains("List") || type.contains("Array") -> "array"
//            type.contains("Map") -> "object"
//            else -> "string"
//        }
//    }
//
//    /**
//     * Get all symbols that extend or implement a specific type
//     */
//    private fun getSymbolsWithSupertype(resolver: Resolver, supertype: String): Sequence<KSClassDeclaration> {
//        return resolver
//            .getAllFiles()
//            .flatMap { it.declarations }
//            .filterIsInstance<KSClassDeclaration>()
//            .filter { classDecl ->
//                classDecl.superTypes
//                    .any { it.resolve().declaration.qualifiedName?.asString() == supertype }
//            }
//    }
//
//    /**
//     * Check if a class declaration is abstract
//     */
//    private fun KSClassDeclaration.isAbstract(): Boolean {
//        return this.modifiers.contains(Modifier.ABSTRACT)
//    }
//}

///**
// * Provider for the DirectApiDocProcessor
// */
//class DirectApiDocProcessorProvider : SymbolProcessorProvider {
//    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
//        return DirectApiDocProcessor(
//            environment.codeGenerator,
//            environment.logger
//        )
//    }
//}
