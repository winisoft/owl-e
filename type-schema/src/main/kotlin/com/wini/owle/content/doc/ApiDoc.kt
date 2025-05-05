package com.wini.owle.content.doc

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Represents documentation for an API endpoint class.
 * 
 * This captures all information about an endpoint implementation including:
 * - The routes it handles
 * - The HTTP methods supported at each route
 * - The DTO type it operates on
 * - Associated validation rules and error responses
 */
@Serializable
data class ApiEndpointDoc(
    val name: String,
    val description: String,
    val basePath: String,
    val routes: List<ApiRouteDoc>,
    val dtoType: String,
    val tags: List<String> = emptyList()
)

/**
 * Represents documentation for a specific API route.
 * 
 * This captures information about a specific URL path and the operations
 * available on that path.
 */
@Serializable
data class ApiRouteDoc(
    val path: String,
    val operations: List<ApiOperationDoc>
)

/**
 * Represents documentation for a specific API operation (HTTP method + path).
 * 
 * This captures information about a specific HTTP method on a specific path,
 * including:
 * - Request parameters
 * - Request body schema
 * - Response body schema
 * - Possible error responses
 */
@Serializable
data class ApiOperationDoc(
    val method: String,
    val summary: String,
    val description: String,
    val parameters: List<ApiParameterDoc> = emptyList(),
    val requestBody: ApiSchemaDoc? = null,
    val responses: Map<Int, ApiResponseDoc> = emptyMap()
)

/**
 * Represents documentation for a parameter in an API operation.
 */
@Serializable
data class ApiParameterDoc(
    val name: String,
    val description: String,
    val required: Boolean,
    val schema: ApiSchemaDoc,
    val location: ParameterLocation = ParameterLocation.PATH
)

/**
 * Represents the location of a parameter in an API request.
 */
@Serializable
enum class ParameterLocation {
    PATH, QUERY, HEADER, COOKIE
}

/**
 * Represents documentation for a schema used in an API.
 */
@Serializable
data class ApiSchemaDoc(
    val type: String,
    val format: String? = null,
    val description: String? = null,
    val properties: Map<String, ApiSchemaDoc> = emptyMap(),
    val items: ApiSchemaDoc? = null,
    val ref: String? = null,
    val required: List<String> = emptyList(),
    val example: String? = null
)

/**
 * Represents documentation for an API response.
 */
@Serializable
data class ApiResponseDoc(
    val description: String,
    val content: Map<String, ApiMediaTypeDoc> = emptyMap()
)

/**
 * Represents documentation for a media type in an API response.
 */
@Serializable
data class ApiMediaTypeDoc(
    val schema: ApiSchemaDoc,
    val example: String? = null
)

/**
 * Represents a complete OpenAPI specification.
 */
@Serializable
data class OpenApiSpec(
    val openapi: String = "3.0.0",
    val info: Info,
    val paths: Map<String, Map<String, @Contextual Any>>,
    val components: Map<String, @Contextual Any> = emptyMap(),
    val tags: List<Tag> = emptyList()
) {
    @Serializable
    data class Info(
        val title: String,
        val version: String,
        val description: String
    )
    
    @Serializable
    data class Tag(
        val name: String,
        val description: String
    )
    
    fun toJson(): String {
        // In a real implementation, this would use kotlinx.serialization
        // For now, returning a basic stub
        return """
        {
          "openapi": "3.0.0",
          "info": {
            "title": "${info.title}",
            "version": "${info.version}",
            "description": "${info.description}"
          },
          "paths": {}
        }
        """.trimIndent()
    }
}
