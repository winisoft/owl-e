package com.wini.owle.ksp.doc.oas

import com.wini.owle.ksp.doc.oas.ParameterLocation
import com.wini.owle.ksp.doc.oas.schema.ApiSchemaDoc
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