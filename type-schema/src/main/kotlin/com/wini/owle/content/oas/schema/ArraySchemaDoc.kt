package com.wini.owle.content.oas.schema

import com.wini.owle.content.oas.schema.ApiSchemaDoc
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

@Serializable
data class ArraySchemaDoc(
    val items: ApiSchemaDoc,
    val description: String? = null,
    val example: String? = null
) : ApiSchemaDoc() {
    override fun toJson(): JsonObject = buildJsonObject {
        put("type", JsonPrimitive("array"))
        description?.let { put("description", JsonPrimitive(it)) }
        put("items", items.toJson())
        example?.let { put("example", JsonPrimitive(it)) }
    }
}