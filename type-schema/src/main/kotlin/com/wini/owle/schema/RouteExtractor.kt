package com.wini.owle.schema

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration


/**
 * Specialized utility for extracting route information from Ktor route definitions.
 * 
 * This class analyzes the register() method body to find route definitions like:
 * - get("/api/package") { ... }
 * - post("/api/package/{id}") { ... }
 * 
 * It extracts path patterns, HTTP methods, and maps them to OwleApiCall types to infer
 * request/response structures.
 */
class RouteExtractor(private val logger: KSPLogger) {

    /**
     * Extract routes from a BaseEndpointImpl subclass
     */
    fun extractRoutes(endpointClass: KSClassDeclaration): List<RouteInfo> {
        // Find the register method
        val registerFunction = endpointClass.getAllFunctions()
            .firstOrNull { it.simpleName.asString() == "register" }
            ?: return emptyList()
            
        // Get the function source code
        // We can't access source directly, we need to use the function body
        val functionSource = try {
            registerFunction.containingFile?.toString() ?: return emptyList()
        } catch (e: Exception) {
            logger.error("Failed to access source code for ${endpointClass.qualifiedName?.asString()}")
            return emptyList()
        }
        
        // Parse the routing DSL to extract route patterns
        return parseRoutingDsl(functionSource, endpointClass.simpleName.asString())
    }
    
    /**
     * Parse Ktor routing DSL to extract route information
     * 
     * This is a simplified implementation. A production-ready version would use
     * KSP's visitor pattern to analyze the AST properly.
     */
    private fun parseRoutingDsl(source: String, className: String): List<RouteInfo> {
        val routes = mutableListOf<RouteInfo>()
        
        // Simple regex-based extraction - just for demonstration
        // A real implementation would traverse the KSP AST
        val httpMethodPattern = "(get|post|put|delete|patch)\\(\\s*\"([^\"]+)\"\\s*\\)\\s*\\{[\\s\\S]*?call\\.execute\\(\\s*OwleApiCall\\.(\\w+)\\s*\\)".toRegex()
        
        httpMethodPattern.findAll(source).forEach { matchResult ->
            val (method, path, callType) = matchResult.destructured
            
            // Map OwleApiCall type to response structure
            val responseType = when (callType) {
                "GetOne", "CreateOne", "UpdateOne" -> "Single DTO"
                "GetMany", "CreateMany", "UpdateMany" -> "List of DTOs"
                "DeleteOne", "DeleteMany" -> "No content"
                else -> "Unknown"
            }
            
            routes.add(
                RouteInfo(
                    path = path,
                    method = method.uppercase(),
                    callType = callType,
                    responseType = responseType
                )
            )
        }
        
        logger.info("Extracted ${routes.size} routes from $className")
        return routes
    }
    
    data class RouteInfo(
        val path: String,
        val method: String,
        val callType: String,
        val responseType: String,
        val description: String = "No description available"
    )
}
