package com.wini.owle.schema

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import com.wini.owle.content.doc.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedWriter

/**
 * A symbol processor that extracts API documentation from the codebase structure.
 * 
 * Instead of relying heavily on annotations, this processor infers API structure by:
 * 1. Finding all classes extending BaseEndpointImpl
 * 2. Extracting route patterns from their register() methods
 * 3. Mapping DTOs to endpoints based on type parameters
 * 4. Extracting validation rules from DTOs
 * 5. Mapping possible error responses based on error handling in BaseEndpointImpl
 */
class ApiDocProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {
    
    // Store collected endpoint documentation
    private val endpoints = mutableListOf<ApiEndpointDoc>()
    
    // Store collected model documentation
    private val models = mutableMapOf<String, DtoModelDoc>()
    
    // Store collected error documentation
    private val errors = mutableListOf<OwleErrorDoc>()
    
    // Route extractor for parsing Ktor routing DSL
    private val routeExtractor = RouteExtractor(logger)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.info("Starting API documentation extraction")
        
        // Process DTO models first
        processModels(resolver)
        
        // Process errors
        processErrors(resolver)
        
        // Find all endpoint implementations and extract their routes
        processEndpoints(resolver)
        
        // Generate OpenAPI spec
        generateOpenApiSpec()
        
        // Generate individual endpoint docs
        endpoints.forEach { endpoint ->
            generateEndpointDoc(endpoint)
        }
        
        logger.info("Completed API documentation extraction")
        return emptyList()
    }
    
    private fun processModels(resolver: Resolver) {
        logger.info("Processing DTO models")
        
        // Find all classes that implement BaseDto
        val baseDtoName = "com.wini.owle.dto.BaseDto"
        val baseDtoInterface = resolver.getClassDeclarationByName(
            resolver.getKSNameFromString(baseDtoName)
        )
        
        if (baseDtoInterface == null) {
            logger.error("Could not find $baseDtoName interface")
            return
        }
        
        val dtos = resolver
            .getAllFiles()
            .flatMap { it.declarations }
            .filterIsInstance<KSClassDeclaration>()
            .filter { classDecl ->
                classDecl.superTypes
                    .any { it.resolve().declaration.qualifiedName?.asString() == baseDtoName }
            }
            
        logger.info("Found ${dtos.count()} DTO models")
        
        dtos.forEach { dtoClass ->
            val model = buildDtoDoc(dtoClass)
            models[dtoClass.qualifiedName?.asString() ?: "unknown"] = model
        }
    }
    
    private fun buildDtoDoc(dtoClass: KSClassDeclaration): DtoModelDoc {
        val name = dtoClass.simpleName.asString()
        val description = dtoClass.docString?.trim() ?: "No documentation available"
        
        // Extract properties and their constraints
        val properties = dtoClass.getAllProperties().map { prop ->
            val propName = prop.simpleName.asString()
            val type = prop.type.resolve().declaration.qualifiedName?.asString() ?: "Unknown"
            val doc = prop.docString?.trim() ?: "No documentation available"
            
            // Extract validation constraints from annotations
            val constraints = prop.annotations.mapNotNull { annotation ->
                val annClassName = annotation.annotationType.resolve().declaration.qualifiedName?.asString() ?: return@mapNotNull null
                
                // Only process validation annotations (those in com.wini.owle.ksp package)
                if (!annClassName.startsWith("com.wini.owle.ksp")) return@mapNotNull null
                
                // Extract error code and other attributes
                val errorCode = annotation.arguments.firstOrNull { it.name?.asString() == "errorCode" }?.value as? String 
                    ?: annClassName.substringAfterLast('.')
                
                val message = annotation.arguments.firstOrNull { it.name?.asString() == "message" }?.value as? String
                    ?: "$propName must meet validation requirement $errorCode"
                
                // Extract other attributes as a map
                val attrMap: Map<String, String> = annotation.arguments.associate { arg ->
                    (arg.name?.asString() ?: "") to (arg.value?.toString() ?: "")
                }.filterKeys { it != "errorCode" && it != "message" }
                
                OwleErrorDoc(
                    errorCode = errorCode,
                    message = message,
                    attributes = attrMap.mapValues { it.value.toString() }
                )
            }.toList()
            
            ModelPropertyDoc(
                name = propName,
                type = type,
                description = doc,
                constraints = constraints
            )
        }.toList()
        
        return DtoModelDoc(name, description, properties)
    }
    
    private fun processErrors(resolver: Resolver) {
        logger.info("Processing error types")
        
        // Find all classes that implement OwleError
        val owleErrorName = "com.wini.owle.error.OwleError"
        val owleErrorInterface = resolver.getClassDeclarationByName(
            resolver.getKSNameFromString(owleErrorName)
        )
        
        if (owleErrorInterface == null) {
            logger.error("Could not find $owleErrorName interface")
            return
        }
        
        val errorClasses = resolver
            .getAllFiles()
            .flatMap { it.declarations }
            .filterIsInstance<KSClassDeclaration>()
            .filter { classDecl ->
                classDecl.superTypes
                    .any { it.resolve().declaration.qualifiedName?.asString() == owleErrorName }
            }
            
        logger.info("Found ${errorClasses.count()} error types")
        
        // Here we would extract error information from classes
        // For this sample implementation, we're not doing the full extraction
    }
    
    private fun processEndpoints(resolver: Resolver) {
        logger.info("Processing API endpoints")
        
        // Find all classes that extend BaseEndpointImpl
        val baseEndpointName = "com.wini.owle.api.BaseEndpointImpl"
        val baseEndpointClass = resolver.getClassDeclarationByName(
            resolver.getKSNameFromString(baseEndpointName)
        )
        
        if (baseEndpointClass == null) {
            logger.error("Could not find $baseEndpointName class")
            return
        }
        
        // Get all classes extending BaseEndpointImpl
        val endpointClasses = resolver
            .getAllFiles()
            .flatMap { it.declarations }
            .filterIsInstance<KSClassDeclaration>()
            .filter { classDecl ->
                classDecl.superTypes
                    .any { it.resolve().declaration.qualifiedName?.asString() == baseEndpointName }
            }
        
        logger.info("Found ${endpointClasses.count()} endpoint classes")
        
        endpointClasses.forEach { endpointClass ->
            processEndpoint(endpointClass, resolver)
        }
    }
    
    private fun processEndpoint(endpointClass: KSClassDeclaration, resolver: Resolver) {
        val className = endpointClass.simpleName.asString()
        val packageName = endpointClass.packageName.asString()
        logger.info("Processing endpoint: $packageName.$className")
        
        // Extract the DTO type from type parameters
        val dtoType = extractDtoType(endpointClass)
        logger.info("  DTO type: $dtoType")
        
        // Look for any API documentation annotations
        val description = endpointClass.annotations
            .firstOrNull { it.annotationType.resolve().declaration.qualifiedName?.asString() == "com.wini.owle.content.OwleApiEndpoint" }
            ?.arguments
            ?.firstOrNull { it.name?.asString() == "description" }
            ?.value as? String 
            ?: endpointClass.docString 
            ?: "No description available"
        
        // Extract tags from annotation if present
        val tags = (endpointClass.annotations
            .firstOrNull { it.annotationType.resolve().declaration.qualifiedName?.asString() == "com.wini.owle.content.OwleApiEndpoint" }
            ?.arguments
            ?.firstOrNull { it.name?.asString() == "tags" }
            ?.value as? ArrayList<*>)
            ?.filterIsInstance<String>()
            ?: emptyList()
        
        // Extract routes using the RouteExtractor
        val routes = routeExtractor.extractRoutes(endpointClass)
        logger.info("  Found ${routes.size} routes")
        
        // Determine base path (common prefix among routes)
        val basePath = if (routes.isNotEmpty()) {
            findCommonPathPrefix(routes.map { it.path })
        } else {
            ""
        }
        
        // Convert to ApiRouteDoc objects
        val routeDocs = routes.map { route ->
            // Convert to relative path if there's a basePath
            val relativePath = if (basePath.isNotEmpty() && route.path.startsWith(basePath)) {
                route.path.substring(basePath.length)
            } else {
                route.path
            }
            
            // Build ApiOperationDoc for this route
            val operation = ApiOperationDoc(
                method = route.method,
                summary = route.callType, // This would be better with annotation data
                description = route.description,
                responses = mapOf(
                    200 to ApiResponseDoc(
                        description = "Successful operation",
                        content = mapOf(
                            "application/json" to ApiMediaTypeDoc(
                                schema = ApiSchemaDoc(
                                    type = when (route.responseType) {
                                        "List of DTOs" -> "array"
                                        "Single DTO" -> "object"
                                        else -> "object"
                                    },
                                    items = if (route.responseType == "List of DTOs") {
                                        ApiSchemaDoc(type = "object", ref = "#/components/schemas/$dtoType")
                                    } else null,
                                    ref = if (route.responseType == "Single DTO") {
                                        "#/components/schemas/$dtoType"
                                    } else null
                                )
                            )
                        )
                    )
                )
            )
            
            ApiRouteDoc(
                path = relativePath,
                operations = listOf(operation)
            )
        }
        
        // Create endpoint documentation
        val endpointDoc = ApiEndpointDoc(
            name = className,
            description = description,
            basePath = basePath,
            routes = routeDocs,
            dtoType = dtoType,
            tags = tags
        )
        
        endpoints.add(endpointDoc)
    }
    
    private fun extractDtoType(endpointClass: KSClassDeclaration): String {
        // Extract the DTO type from BaseEndpointImpl<ID, T: BaseDto>
        return endpointClass.superTypes
            .filter { it.resolve().declaration.qualifiedName?.asString()?.contains("BaseEndpointImpl") == true }
            .firstOrNull()
            ?.element
            ?.typeArguments
            ?.getOrNull(1)
            ?.type
            ?.resolve()
            ?.declaration
            ?.simpleName
            ?.asString() ?: "unknown"
    }
    
    private fun findCommonPathPrefix(paths: List<String>): String {
        if (paths.isEmpty()) return ""
        if (paths.size == 1) return paths[0]
        
        // Find the common prefix of all paths
        val firstPath = paths[0]
        var prefixEnd = 0
        
        // Find the common prefix length
        for (i in firstPath.indices) {
            val char = firstPath[i]
            if (paths.all { it.length > i && it[i] == char }) {
                prefixEnd = i + 1
            } else {
                break
            }
        }
        
        // If the common prefix doesn't end with '/', find the last '/'
        var commonPrefix = firstPath.substring(0, prefixEnd)
        if (!commonPrefix.endsWith("/")) {
            val lastSlash = commonPrefix.lastIndexOf('/')
            if (lastSlash >= 0) {
                commonPrefix = commonPrefix.substring(0, lastSlash + 1)
            }
        }
        
        return commonPrefix
    }
    
    private fun generateEndpointDoc(endpoint: ApiEndpointDoc) {
        val file = codeGenerator.createNewFile(
            Dependencies(false),
            "com.wini.owle.apidoc",
            "${endpoint.name}ApiDoc",
            "md"
        )
        
        file.bufferedWriter().use { writer ->
            writer.appendLine("# ${endpoint.name} API")
            writer.appendLine("---")
            writer.appendLine(endpoint.description)
            writer.appendLine()
            
            if (endpoint.tags.isNotEmpty()) {
                writer.appendLine("**Tags:** ${endpoint.tags.joinToString(", ")}")
                writer.appendLine()
            }
            
            writer.appendLine("## Base Path")
            writer.appendLine("`${endpoint.basePath}`")
            writer.appendLine()
            
            writer.appendLine("## Data Type")
            writer.appendLine("This endpoint operates on: `${endpoint.dtoType}`")
            writer.appendLine()
            
            writer.appendLine("## Routes")
            
            endpoint.routes.forEach { route ->
                route.operations.forEach { operation ->
                    writer.appendLine("### ${operation.method} ${route.path}")
                    writer.appendLine("**Summary:** ${operation.summary}")
                    writer.appendLine()
                    writer.appendLine(operation.description)
                    writer.appendLine()
                    
                    if (operation.parameters.isNotEmpty()) {
                        writer.appendLine("#### Parameters")
                        operation.parameters.forEach { param ->
                            writer.appendLine("- `${param.name}` (${param.location.name.lowercase()}, ${if (param.required) "required" else "optional"}): ${param.description}")
                        }
                        writer.appendLine()
                    }
                    
                    writer.appendLine("#### Responses")
                    operation.responses.forEach { (code, response) ->
                        writer.appendLine("**$code:** ${response.description}")
                    }
                    writer.appendLine()
                }
            }
        }
    }
    
    private fun generateOpenApiSpec() {
        val file = codeGenerator.createNewFile(
            Dependencies(false),
            "com.wini.owle.openapi",
            "openapi",
            "json"
        )
        
        // Convert endpoints to OpenAPI path structure
        val paths = mutableMapOf<String, MutableMap<String, Any>>()
        
        endpoints.forEach { endpoint ->
            endpoint.routes.forEach { route ->
                val fullPath = endpoint.basePath + route.path
                
                val pathItem = paths.getOrPut(fullPath) { mutableMapOf() }
                
                route.operations.forEach { operation ->
                    val method = operation.method.lowercase()
                    
                    // Convert operation to OpenAPI format
                    // This is a simplified version
                    val openApiOperation = mapOf(
                        "summary" to operation.summary,
                        "description" to operation.description,
                        "tags" to endpoint.tags,
                        "responses" to operation.responses.mapValues { (_, response) ->
                            mapOf(
                                "description" to response.description,
                                "content" to response.content.mapValues { (_, mediaType) ->
                                    mapOf(
                                        "schema" to buildSchemaMap(mediaType.schema)
                                    )
                                }
                            )
                        }
                    )
                    
                    pathItem[method] = openApiOperation
                }
            }
        }
        
        // Build components section with schemas
        val schemas = models.mapValues { (_, model) ->
            mapOf(
                "type" to "object",
                "properties" to model.properties.associate { prop ->
                    prop.name to mapOf(
                        "type" to getOpenApiType(prop.type),
                        "description" to prop.description
                    )
                }
            )
        }
        
        val components = mapOf(
            "schemas" to schemas
        )
        
        // Generate final OpenAPI spec
        val openApiSpec = mapOf(
            "openapi" to "3.0.0",
            "info" to mapOf(
                "title" to "Owle API",
                "version" to "1.0.0",
                "description" to "API documentation automatically generated from codebase structure"
            ),
            "paths" to paths,
            "components" to components
        )
        
        // Write out the JSON
        file.bufferedWriter().use { writer ->
            // In a real implementation, we'd use proper JSON serialization
            // For this example, we're writing a simplified version
            val json = Json { prettyPrint = true }
            writer.write(json.encodeToString(openApiSpec))
        }
    }
    
    private fun buildSchemaMap(schema: ApiSchemaDoc): Map<String, Any?> {
        val result = mutableMapOf<String, Any?>()
        
        if (schema.type != null) result["type"] = schema.type
        if (schema.format != null) result["format"] = schema.format
        if (schema.description != null) result["description"] = schema.description
        if (schema.ref != null) result["\$ref"] = schema.ref
        
        if (schema.properties.isNotEmpty()) {
            result["properties"] = schema.properties.mapValues { buildSchemaMap(it.value) }
        }
        
        if (schema.items != null) {
            result["items"] = buildSchemaMap(schema.items)
        }
        
        if (schema.required.isNotEmpty()) {
            result["required"] = schema.required
        }
        
        return result
    }
    
    private fun getOpenApiType(kotlinType: String): String {
        return when {
            kotlinType.contains("String") -> "string"
            kotlinType.contains("Int") || kotlinType.contains("Long") -> "integer"
            kotlinType.contains("Double") || kotlinType.contains("Float") -> "number"
            kotlinType.contains("Boolean") -> "boolean"
            kotlinType.contains("List") || kotlinType.contains("Array") -> "array"
            else -> "object"
        }
    }
}

class ApiDocProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return ApiDocProcessor(
            environment.codeGenerator,
            environment.logger
        )
    }
}
