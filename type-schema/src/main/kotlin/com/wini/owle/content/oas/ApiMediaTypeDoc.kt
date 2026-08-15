package com.wini.owle.content.oas

import com.wini.owle.content.oas.schema.ApiSchemaDoc
import kotlinx.serialization.Serializable

/**
 * Represents documentation for a media type in an API response.
 */
@Serializable
data class ApiMediaTypeDoc(
    val schema: ApiSchemaDoc,
    val example: String? = null
)