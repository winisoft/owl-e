package com.wini.owle.content.oas.schema

import com.wini.owle.content.oas.schema.ApiSchemaDoc
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlin.collections.iterator

@Serializable
data class ObjectSchemaDoc(
    val description: String? = null,
    val properties: Map<String, ApiSchemaDoc> = emptyMap(),
    val required: List<String> = emptyList(),
    val example: String? = null
) : ApiSchemaDoc() {
    override fun toJson(): JsonObject = buildJsonObject {
        put("type", JsonPrimitive("object"))
        description?.let { put("description", JsonPrimitive(it)) }
        put("properties", buildJsonObject {
            for ((name, schema) in properties) {
                put(name, schema.toJson())
            }
        })
        if (required.isNotEmpty()) {
            put("required", JsonArray(required.map { JsonPrimitive(it) }))
        }
        example?.let { put("example", JsonPrimitive(it)) }
    }
}


