package com.wini.owle.schema

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.wini.owle.content.doc.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.io.File

/**
 * A simplified API documentation processor that focuses on just extracting routes
 * from endpoints and generating a basic OpenAPI specification.
 */
class SimpleApiDocProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {
    
    // Store collected endpoint documentation
    private val endpoints = mutableListOf<ApiEndpointDoc>()
    
    // Route extractor for parsing Ktor routing DSL
    private val routeExtractor = RouteExtractor(logger)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        try {
            logger.info("Starting API route extraction")
            
            // Find all endpoint implementations and extract their routes
            processEndpoints(resolver)
            
            // Generate OpenAPI spec only if we found actual endpoints
            if (endpoints.isNotEmpty()) {
                generateOpenApiSpec()
            } else {
                logger.warn("No API endpoints found. Skipping OpenAPI generation.")
            }
            
        } catch (e: Exception) {
            logger.error("Error in API route extraction: ${e.message}")
            e.printStackTrace()
        }
        
        logger.info("Completed API route extraction")
        return emptyList()
    }
    
    /**
     * Process all endpoint classes to extract API routes
     */
    private fun processEndpoints(resolver: Resolver) {
        logger.info("Processing API endpoints")
        
        // Find all classes that extend BaseEndpointImpl
        val baseEndpointName = "com.wini.owle.api.BaseEndpointImpl"
        val endpointClasses = getSymbolsWithSupertype(resolver, baseEndpointName)
            .filterIsInstance<KSClassDeclaration>()
            
        logger.info("Found ${endpointClasses.count()} API endpoint classes")
        
        endpointClasses.forEach { endpointClass ->
            try {
                processEndpoint(endpointClass)
            } catch (e: Exception) {
                logger.error("Error processing endpoint ${endpointClass.simpleName.asString()}: ${e.message}")
            }
        }
    }
    
    /**
     * Process a single endpoint class
     */
    private fun processEndpoint(endpointClass: KSClassDeclaration) {
        val endpointName = endpointClass.simpleName.asString()
        logger.info("Processing endpoint: $endpointName")
        
        // Skip abstract classes and interfaces
        if (endpointClass.modifiers.contains(Modifier.ABSTRACT) || 
            endpointClass.classKind == ClassKind.INTERFACE) {
            logger.info("Skipping abstract class or interface: $endpointName")
            return
        }
        
        // Extract endpoint documentation from KDoc
        val description = endpointClass.docString?.trim() ?: "No documentation available"
        
        // Extract routes by analyzing register() method
        val routes = extractRoutes(endpointClass)
        
        logger.info("Found ${routes.size} routes for endpoint $endpointName")
        
        // Create endpoint documentation
        val endpointDoc = ApiEndpointDoc(
            name = endpointName,
            description = description,
            basePath = "/api/${endpointName.lowercase()}",
            dtoType = "${endpointName}Dto",
            tags = listOf(endpointName),
            routes = routes
        )
        
        endpoints.add(endpointDoc)
    }
    
    /**
     * Extract routes from an endpoint class's register() method
     */
    private fun extractRoutes(endpointClass: KSClassDeclaration): List<ApiRouteDoc> {
        // Group routes by path
        val routesByPath = routeExtractor.extractRoutes(endpointClass)
            .groupBy { it.path }
        
        // Convert to ApiRouteDoc format
        return routesByPath.map { (path, routeInfos) ->
            ApiRouteDoc(
                path = path,
                operations = routeInfos.map { routeInfo ->
                    ApiOperationDoc(
                        method = routeInfo.method.lowercase(),
                        summary = routeInfo.callType,
                        description = routeInfo.description
                    )
                }
            )
        }
    }
    
    /**
     * Generate a minimal OpenAPI specification document
     */
    private fun generateOpenApiSpec() {
        logger.info("Generating minimal OpenAPI specification")
        
        try {
            val openApiSpec = OpenApiSpec(
                openapi = "3.0.0",
                info = OpenApiSpec.Info(
                    title = "Owl-e API",
                    description = "Owl-e API Documentation",
                    version = "1.0.0"
                ),
                paths = buildPathMap(),
                components = emptyMap(),
                tags = endpoints.map { OpenApiSpec.Tag(name = it.name, description = it.description) }
            )
            
            // Write OpenAPI spec to file
            val outputDir = "api-docs"
            
            val directory = File(outputDir)
            if (!directory.exists()) {
                directory.mkdirs()
            }
            
            val json = Json { prettyPrint = true; encodeDefaults = true }
            val openApiJson = json.encodeToString(openApiSpec)
            
            val outputPath = "$outputDir/openapi.json"
            File(outputPath).writeText(openApiJson)
            
            logger.info("Basic OpenAPI specification generated at $outputPath")
        } catch (e: Exception) {
            logger.error("Error writing OpenAPI specification: ${e.message}")
        }
    }
    
    /**
     * Get all symbols that extend or implement a specific type
     */
    private fun getSymbolsWithSupertype(resolver: Resolver, supertype: String): Sequence<KSClassDeclaration> {
        return resolver
            .getAllFiles()
            .flatMap { it.declarations }
            .filterIsInstance<KSClassDeclaration>()
            .filter { classDecl ->
                classDecl.superTypes
                    .any { it.resolve().declaration.qualifiedName?.asString() == supertype }
            }
    }
    
    /**
     * Build a map of paths to operations for the OpenAPI spec
     */
    private fun buildPathMap(): Map<String, JsonObject> {
        val result = mutableMapOf<String, JsonObject>()
        
        // Group endpoints by path
        val pathMap = mutableMapOf<String, MutableList<Pair<String, ApiOperationDoc>>>()
        
        endpoints.forEach { endpoint ->
            endpoint.routes.forEach { route ->
                val path = route.path
                route.operations.forEach { operation ->
                    val operations = pathMap.getOrPut(path) { mutableListOf() }
                    operations.add(Pair(operation.method, operation))
                }
            }
        }
        
        // Add each path
        pathMap.forEach { (path, operations) ->
            result[path] = buildJsonObject {
                operations.forEach { (method, operation) ->
                    put(method, buildJsonObject {
                        put("summary", operation.summary)
                        put("description", operation.description.ifEmpty { "No description available" })
                        
                        // Add tags
                        val relevantEndpoint = endpoints.first { endpoint ->
                            endpoint.routes.any { it.path == path && it.operations.any { op -> op.summary == operation.summary } }
                        }
                        
                        put("tags", buildJsonArray {
                            relevantEndpoint.tags.forEach { add(it) }
                        })
                        
                        // Add a minimal response section
                        put("responses", buildJsonObject {
                            put("200", buildJsonObject {
                                put("description", "Successful operation")
                            })
                        })
                    })
                }
            }
        }
        
        return result
    }
}

/**
 * Provider for the SimpleApiDocProcessor
 */
class SimpleApiDocProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return SimpleApiDocProcessor(
            environment.codeGenerator,
            environment.logger
        )
    }
}
