package com.wini.owle.ksp.doc.oas.schema

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Represents documentation for a schema used in an API.
 */
@Serializable
sealed class ApiSchemaDoc {
    abstract fun toJson(): JsonObject
}