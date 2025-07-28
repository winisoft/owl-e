package com.wini.owle.ksp.doc.oas.schema

import com.wini.owle.ksp.doc.oas.schema.ApiSchemaDoc
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

@Serializable
data class PrimitiveSchemaDoc(
    val type: String, // e.g., "string", "integer", "boolean"
    val format: String? = null,
    val description: String? = null,
    val example: String? = null
) : ApiSchemaDoc() {
    override fun toJson(): JsonObject = buildJsonObject {
        put("type", JsonPrimitive(type))
        format?.let { put("format", JsonPrimitive(it)) }
        description?.let { put("description", JsonPrimitive(it)) }
        example?.let { put("example", JsonPrimitive(it)) }
    }
}