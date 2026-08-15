package com.wini.owle.content.oas

import com.wini.owle.content.oas.ParameterLocation
import com.wini.owle.content.oas.schema.ApiSchemaDoc
import kotlinx.serialization.Serializable

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