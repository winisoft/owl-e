package com.wini.owle.ksp.doc.oas

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator

/**
 * Represents a complete OpenAPI specification.
 */
@Serializable
data class OpenApiSpec(
    val openapi: String = "3.0.0",
    val info: Info,
    val paths: Map<String, JsonObject>,
    val components: Map<String, JsonObject> = emptyMap(),
    val tags: List<Tag> = emptyList()
) {

    @Serializable
    data class Info(
        val title: String,
        val version: String,
        val description: String
    )

    @Serializable
    data class Tag(
        val name: String,
        val description: String
    )

}