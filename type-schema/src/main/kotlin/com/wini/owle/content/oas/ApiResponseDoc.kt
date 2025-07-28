package com.wini.owle.ksp.doc.oas

import kotlinx.serialization.Serializable

/**
 * Represents documentation for an API response.
 */
@Serializable
data class ApiResponseDoc(
    val description: String,
    val content: Map<String, ApiMediaTypeDoc> = emptyMap()
)