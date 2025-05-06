package com.wini.owle.schema

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.content.doc.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
//
///**
// * Enhanced API documentation processor that leverages the model registry
// * to integrate model information from the common module.
// */
//class EnhancedApiDocProcessor(
//    private val codeGenerator: CodeGenerator,
//    private val logger: KSPLogger,
//    private val options: Map<String, String>
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
//    // Model registry loader
//    private val registryLoader = ModelRegistryLoader(logger)
//
//    // Registry file path
//    private val registryPath by lazy {
//        options["modelRegistryPath"] ?: "build/generated/owl-e/model-registry.json"
//    }
//
//    override fun process(resolver: Resolver): List<KSAnnotated> {
//        try {
//            logger.info("Starting enhanced API documentation extraction")
//
//            // Load model information from registry file
//            val registryData = registryLoader.loadModelRegistry(registryPath)
//            models.putAll(registryData.models)
//            errors.putAll(registryData.errors)
//
//            logger.info("Imported ${registryData.models.size} models and ${registryData.errors.size} errors from registry")
//
//            // Process additional models defined in this module
//            processLocalModels(resolver)
//
//            // Process additional errors defined in this module
//            processLocalErrors(resolver)
//
//            // Find all endpoint implementations and extract their routes
//            processEndpoints(resolver)
//
//            // Don't generate for abstract base classes
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
//            logger.error("Error in enhanced API doc processing: ${e.message}")
//            e.printStackTrace()
//        }
//
//        logger.info("Completed enhanced API documentation extraction")
//        return emptyList()
//    }
//
//    /**
//     * Process models defined in the current module (in addition to those loaded from registry)
//     */
//    private fun processLocalModels(resolver: Resolver) {
//        logger.info("Processing local DTO models")
//
//        // Find all classes that implement BaseDto in this module
//        val baseDtoName = "com.wini.owle.dto.BaseDto"
//        val baseDtoInterface = resolver.getClassDeclarationByName(
//            resolver.getKSNameFromString(baseDtoName)
//        )
//
//        if (baseDtoInterface == null) {
//            logger.warn("Could not find $baseDtoName interface in current module")
//            return
//        }
//
//        val dtos = getSymbolsWithSupertype(resolver, baseDtoName)
//            .filterIsInstance<KSClassDeclaration>()
//
//        logger.info("Found ${dtos.count()} additional DTO models in current module")
//
//        dtos.forEach { dtoClass ->
//            val qualifiedName = dtoClass.qualifiedName?.asString() ?: return@forEach
//
//            // Skip if already loaded from registry
//            if (models.containsKey(qualifiedName)) {
//                logger.info("Model $qualifiedName already loaded from registry, skipping local processing")
//                return@forEach
//            }
//
//            val model = buildDtoDoc(dtoClass)
//            models[qualifiedName] = model
//            logger.info("Added local model: ${model.name}")
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
//     * Build documentation for a DTO class
//     */
//    private fun buildDtoDoc(dtoClass: KSClassDeclaration): DtoModelDoc {
//        val name = dtoClass.simpleName.asString()
//        val description = dtoClass.docString?.trim() ?: "No documentation available"
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
//                // Only process validation annotations (those in com.wini.owle.ksp package)
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
//                    attributes = attrMap.mapValues { it.value.toString() }
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
//     * Process error classes in the current module (in addition to those loaded from registry)
//     */
//    private fun processLocalErrors(resolver: Resolver) {
//        logger.info("Processing local error types")
//
//        val owleErrorName = "com.wini.owle.error.OwleError"
//        val owleErrorInterface = resolver.getClassDeclarationByName(
//            resolver.getKSNameFromString(owleErrorName)
//        )
//
//        if (owleErrorInterface == null) {
//            logger.warn("Could not find $owleErrorName interface in current module")
//            return
//        }
//
//        val errorClasses = getSymbolsWithSupertype(resolver, owleErrorName)
//            .filterIsInstance<KSClassDeclaration>()
//
//        logger.info("Found ${errorClasses.count()} additional error classes in current module")
//
//        errorClasses.forEach { errorClass ->
//            // Only process concrete error implementations, not interfaces or abstract classes
//            if (errorClass.modifiers.contains(com.google.devtools.ksp.symbol.Modifier.ABSTRACT) ||
//                errorClass.classKind == com.google.devtools.ksp.symbol.ClassKind.INTERFACE) {
//                return@forEach
//            }
//
//            val name = errorClass.simpleName.asString()
//
//            // Try to find error code
//            val errorCodeProperty = errorClass.getAllProperties()
//                .firstOrNull { it.simpleName.asString() == "errorCode" }
//
//            val errorCode = when {
//                errorCodeProperty != null -> "ERR_${name.uppercase()}"
//                else -> name
//            }
//
//            // Skip if already loaded from registry
//            if (errors.containsKey(errorCode)) {
//                logger.info("Error $errorCode already loaded from registry, skipping local processing")
//                return@forEach
//            }
//
//            // Try to find message
//            val messageProperty = errorClass.getAllProperties()
//                .firstOrNull { it.simpleName.asString() == "message" }
//
//            val message = when {
//                messageProperty != null -> "Error message defined at runtime"
//                else -> "Unknown error"
//            }
//
//            // Get description from KDoc
//            val description = errorClass.docString?.trim() ?: "No description available"
//
//            val errorDoc = OwleErrorDoc(
//                errorCode = errorCode,
//                message = message,
//                attributes = mapOf("description" to description)
//            )
//
//            errors[errorCode] = errorDoc
//            logger.info("Added local error: $errorCode")
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
//
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
//    private fun processEndpoint(endpointClass: KSClassDeclaration) {
//        val endpointName = endpointClass.simpleName.asString()
//        logger.info("Processing endpoint: $endpointName")
//
//        // Extract endpoint documentation from KDoc or @OwleApiEndpoint annotation
//        val description = endpointClass.docString?.trim() ?: "No documentation available"
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
//        // Get the method content
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
//     * Extract DTO type for an operation type based on type parameter naming convention
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
//        // This is a placeholder - in a real implementation, this would analyze error handling in the endpoint
//        return errors.keys.toList()
//    }
//
//    /**
//     * Generate API documentation as Markdown for a single endpoint
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
//        val minLength = segments.minByOrNull { it.size }?.size ?: 0
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
//     * Generate an OpenAPI specification document
//     */
//    private fun generateOpenApiSpec() {
//        logger.info("Generating OpenAPI specification")
//
//        val openApiSpec = OpenApiSpec(
//            openapi = "3.0.0",
//            info = JsonObject(buildJsonObject {
//                put("title", "Owl-e API")
//                put("description", "Owl-e API Documentation")
//                put("version", "1.0.0")
//            }),
//            servers = JsonArray(buildJsonArray {
//                add(buildJsonObject {
//                    put("url", "/api/v1")
//                    put("description", "Owl-e API v1")
//                })
//            }),
//            paths = buildJsonObject {
//                // Group endpoints by path to consolidate methods
//                val pathMap = mutableMapOf<String, MutableList<Pair<String, ApiOperationDoc>>>()
//
//                endpoints.forEach { endpoint ->
//                    endpoint.routes.forEach { route ->
//                        val path = route.path
//                        val operations = pathMap.getOrPut(path) { mutableListOf() }
//                        operations.add(Pair(route.httpMethod.lowercase(), route.operation))
//                    }
//                }
//
//                // Add each path
//                pathMap.forEach { (path, operations) ->
//                    put(path, buildJsonObject {
//                        operations.forEach { (method, operation) ->
//                            put(method, buildJsonObject {
//                                put("summary", operation.type)
//                                put("description", operation.description.ifEmpty { "No description available" })
//
//                                val relevantEndpoint = endpoints.first { endpoint ->
//                                    endpoint.routes.any { it.path == path && it.operation.type == operation.type }
//                                }
//
//                                // Add tags
//                                put("tags", buildJsonArray {
//                                    relevantEndpoint.tags.forEach { add(it) }
//                                })
//
//                                // Add request body
//                                val requestModel = models[operation.requestType]
//                                if (requestModel != null) {
//                                    put("requestBody", buildJsonObject {
//                                        put("required", true)
//                                        put("content", buildJsonObject {
//                                            put("application/json", buildJsonObject {
//                                                put("schema", buildJsonSchema(requestModel))
//                                            })
//                                        })
//                                    })
//                                }
//
//                                // Add responses
//                                put("responses", buildJsonObject {
//                                    put("200", buildJsonObject {
//                                        put("description", "Successful operation")
//                                        put("content", buildJsonObject {
//                                            put("application/json", buildJsonObject {
//                                                put("schema", buildJsonObject {
//                                                    put("type", "object")
//                                                    put("properties", buildJsonObject {
//                                                        put("success", buildJsonObject {
//                                                            put("type", "boolean")
//                                                            put("description", "Indicates if the operation was successful")
//                                                        })
//                                                        put("data", buildJsonObject {
//                                                            put("type", "object")
//                                                            put("description", "Response data for successful operations")
//                                                        })
//                                                        put("error", buildJsonObject {
//                                                            put("type", "object")
//                                                            put("description", "Error information for failed operations")
//                                                            put("properties", buildJsonObject {
//                                                                put("code", buildJsonObject {
//                                                                    put("type", "string")
//                                                                    put("description", "Error code")
//                                                                })
//                                                                put("message", buildJsonObject {
//                                                                    put("type", "string")
//                                                                    put("description", "Error message")
//                                                                })
//                                                            })
//                                                        })
//                                                    })
//                                                })
//                                            })
//                                        })
//                                    })
//
//                                    // Add error responses
//                                    put("400", buildJsonObject {
//                                        put("description", "Bad request")
//                                    })
//                                    put("401", buildJsonObject {
//                                        put("description", "Unauthorized")
//                                    })
//                                    put("500", buildJsonObject {
//                                        put("description", "Server error")
//                                    })
//                                })
//                            })
//                        }
//                    })
//                }
//            },
//            components = buildJsonObject {
//                put("schemas", buildJsonObject {
//                    // Add all model schemas
//                    models.forEach { (name, model) ->
//                        put(model.name, buildJsonSchema(model))
//                    }
//                })
//
//                put("securitySchemes", buildJsonObject {
//                    put("bearerAuth", buildJsonObject {
//                        put("type", "http")
//                        put("scheme", "bearer")
//                        put("bearerFormat", "JWT")
//                    })
//                })
//            },
//            security = JsonArray(buildJsonArray {
//                add(buildJsonObject {
//                    put("bearerAuth", buildJsonArray { })
//                })
//            })
//        )
//
//        // Write OpenAPI spec to file
//        try {
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
//     * Build a JSON Schema for a model
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
//                            val attrs = constraint.attributes
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
//     * Map Kotlin types to OpenAPI types
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
//}
//
///**
// * Provider for the EnhancedApiDocProcessor
// */
//class EnhancedApiDocProcessorProvider : SymbolProcessorProvider {
//    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
//        return EnhancedApiDocProcessor(
//            environment.codeGenerator,
//            environment.logger,
//            environment.options
//        )
//    }
//}
