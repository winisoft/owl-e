package com.wini.owle.ksp.doc.oas

import com.wini.owle.ksp.doc.oas.schema.ApiSchemaDoc
import kotlinx.serialization.Serializable

/**
 * Represents documentation for a media type in an API response.
 */
@Serializable
data class ApiMediaTypeDoc(
    val schema: ApiSchemaDoc,
    val example: String? = null
)