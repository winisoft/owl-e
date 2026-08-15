package com.wini.owle.content.oas

import kotlinx.serialization.Serializable


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