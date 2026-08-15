package com.wini.owle.content.oas

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