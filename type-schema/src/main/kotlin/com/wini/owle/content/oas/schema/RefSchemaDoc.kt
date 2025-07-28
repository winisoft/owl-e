package com.wini.owle.ksp.doc.oas.schema

import com.wini.owle.ksp.doc.oas.schema.ApiSchemaDoc
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

@Serializable
data class RefSchemaDoc(val ref: String) : ApiSchemaDoc() {
    override fun toJson(): JsonObject = buildJsonObject {
        put("\$ref", JsonPrimitive("#/components/schemas/$ref"))
    }
}