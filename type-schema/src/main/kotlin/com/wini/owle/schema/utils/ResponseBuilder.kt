package com.wini.owle.ksp.utils

import com.wini.owle.content.oas.ApiMediaTypeDoc
import com.wini.owle.content.oas.ApiResponseDoc
import com.wini.owle.content.oas.schema.ApiSchemaDoc
import com.wini.owle.content.oas.schema.ArraySchemaDoc
import com.wini.owle.content.oas.schema.RefSchemaDoc

object ResponseBuilder {

    fun build(method: String, responseType: String?): Map<Int, ApiResponseDoc> {
        val successCode = when (method) {
            "POST" -> 201
            "DELETE" -> 204
            else -> 200
        }

        val content = if (responseType != null) {
            val schema: ApiSchemaDoc = when (responseType) {
                "array" -> ArraySchemaDoc(
                    items = RefSchemaDoc("BaseDto")
                )
                "object" -> RefSchemaDoc("BaseDto")
                else -> error("Unknown responseType: $responseType")
            }

            mapOf(
                "application/json" to ApiMediaTypeDoc(schema)
            )
        } else {
            emptyMap()
        }

        return mapOf(
            successCode to ApiResponseDoc(
                description = "Successful operation",
                content = content
            )
        )
    }
}