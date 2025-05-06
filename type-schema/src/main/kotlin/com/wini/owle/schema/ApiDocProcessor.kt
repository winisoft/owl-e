package com.wini.owle.schema

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.content.doc.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

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
        try {
            logger.info("Starting API documentation extraction")
            
            // Process DTO models first
            processModels(resolver)
            
            // Process errors
            processErrors(resolver)
            
            // Find all endpoint implementations and extract their routes
            processEndpoints(resolver)
            
            // Don't generate for abstract base classes
            endpoints.removeIf { it.name == "BaseEndpointImpl" || it.name == "CompositeIdEndpointImpl" || it.name == "LongIdEndpointImpl" }
            
            // Generate OpenAPI spec only if we found actual endpoints
            if (endpoints.isNotEmpty()) {
                generateOpenApiSpec()
            } else {
                logger.warn("No concrete API endpoints found. Skipping OpenAPI generation.")
            }
            
            // Generate individual endpoint docs
            endpoints.forEach { endpoint ->
                try {
                    generateEndpointDoc(endpoint)
                } catch (e: Exception) {
                    logger.error("Error generating doc for endpoint ${endpoint.name}: ${e.message}")
                }
            }
        } catch (e: Exception) {
            logger.error("Error in API doc processing: ${e.message}")
            e.printStackTrace()
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
        val annotation = endpointClass.annotations
            .firstOrNull { it.annotationType.resolve().declaration.qualifiedName?.asString() == "com.wini.owle.content.OwleApiEndpoint" }
        
        val descriptionFromAnnotation = annotation?.arguments
            ?.firstOrNull { it.name?.asString() == "description" }
            ?.value as? String
            
        val description = descriptionFromAnnotation
            ?: endpointClass.docString
            ?: "API for ${dtoType} management"
        
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
        // Use try-catch to handle the case where the file already exists
        try {
            val file = codeGenerator.createNewFile(
                Dependencies(false),
                "com.wini.owle.openapi",
                "openapi",
                "json"
            )
        
        // Convert endpoints to OpenAPI path structure
        val pathsMap = mutableMapOf<String, JsonObject>()
        
        endpoints.forEach { endpoint ->
            endpoint.routes.forEach { route ->
                val fullPath = endpoint.basePath + route.path
                
                // Build a path item object for each path
                val pathItemBuilder = buildJsonObject {
                    route.operations.forEach { operation ->
                        val method = operation.method.lowercase()
                        
                        // Add each operation to the path item
                        putJsonObject(method) {
                            put("summary", JsonPrimitive(operation.summary))
                            put("description", JsonPrimitive(operation.description))
                            
                            putJsonArray("tags") {
                                endpoint.tags.forEach { tag -> add(JsonPrimitive(tag)) }
                            }
                            
                            putJsonObject("responses") {
                                operation.responses.forEach { (code, response) ->
                                    putJsonObject(code.toString()) {
                                        put("description", JsonPrimitive(response.description))
                                        
                                        putJsonObject("content") {
                                            response.content.forEach { (contentType, mediaType) ->
                                                putJsonObject(contentType) {
                                                    put("schema", buildJsonSchema(mediaType.schema))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                pathsMap[fullPath] = pathItemBuilder
            }
        }
        
        // Build components section with schemas
        val schemasObject = buildJsonObject {
            models.forEach { (_, model) ->
                putJsonObject(model.name) {
                    put("type", JsonPrimitive("object"))
                    
                    putJsonObject("properties") {
                        model.properties.forEach { prop ->
                            putJsonObject(prop.name) {
                                put("type", JsonPrimitive(getOpenApiType(prop.type)))
                                put("description", JsonPrimitive(prop.description))
                            }
                        }
                    }
                }
            }
        }
        
        // Create the map of components
        val componentsMap = mapOf("schemas" to schemasObject)
        
        // Extract real API tags from endpoints for grouping
        val apiTags = endpoints.flatMap { it.tags }.distinct().map { tagName ->
            OpenApiSpec.Tag(
                name = tagName,
                description = "Operations related to $tagName"
            )
        }
        
        // Generate final OpenAPI spec
        val openApiSpec = OpenApiSpec(
            openapi = "3.0.0",
            info = OpenApiSpec.Info(
                title = "Owle API",
                version = "1.0.0",
                description = "API documentation automatically generated from codebase structure"
            ),
            paths = pathsMap,
            components = componentsMap,
            tags = apiTags
        )
        
        // Write out the JSON
        file.bufferedWriter().use { writer ->
            val json = Json { 
                prettyPrint = true 
                encodeDefaults = true
            }
            writer.write(json.encodeToString(openApiSpec))
        }
        
        logger.info("Successfully generated OpenAPI spec with ${pathsMap.size} paths and ${models.size} schemas")
        } catch (e: Exception) {
            logger.error("Error generating OpenAPI spec: ${e.message}")
        }
    }
    
    private fun buildJsonSchema(schema: ApiSchemaDoc): JsonObject {
        return buildJsonObject {
            if (schema.type != null) put("type", JsonPrimitive(schema.type))
            if (schema.format != null) put("format", JsonPrimitive(schema.format))
            if (schema.description != null) put("description", JsonPrimitive(schema.description))
            if (schema.ref != null) put("\$ref", JsonPrimitive(schema.ref))
            
            if (schema.properties.isNotEmpty()) {
                putJsonObject("properties") {
                    schema.properties.forEach { (propName, propSchema) ->
                        put(propName, buildJsonSchema(propSchema))
                    }
                }
            }
            
            if (schema.items != null) {
                put("items", buildJsonSchema(schema.items))
            }
            
            if (schema.required.isNotEmpty()) {
                putJsonArray("required") {
                    schema.required.forEach { add(JsonPrimitive(it)) }
                }
            }
        }
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
