package com.wini.owle.content.oas

import kotlinx.serialization.Serializable

/**
 * Represents documentation for an API operation's request body.
 *
 * Mirrors [ApiResponseDoc] in shape — a description plus media-type-keyed content —
 * with the addition of [required], which the OpenAPI request body object carries.
 */
@Serializable
data class ApiRequestBodyDoc(
    val description: String = "",
    val content: Map<String, ApiMediaTypeDoc> = emptyMap(),
    val required: Boolean = false
)
