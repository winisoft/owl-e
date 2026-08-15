package com.wini.owle.content.oas

import com.wini.owle.content.oas.schema.ApiSchemaDoc
import kotlinx.serialization.Serializable

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
    val requestBody: ApiRequestBodyDoc? = null,
    val responses: Map<Int, ApiResponseDoc> = emptyMap(),
    val tags: List<String>
)