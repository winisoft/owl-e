package com.wini.owle.ksp.processor

import com.google.devtools.ksp.processing.KSPLogger
import com.wini.owle.ksp.doc.oas.ApiEndpointDoc
import com.wini.owle.ksp.doc.oas.OpenApiSpec
import com.wini.owle.ksp.doc.oas.Tag
import kotlinx.serialization.json.* // Import all from json
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator


class OpenApiSpecBuilder(
    val logger: KSPLogger
) {

    fun fromEndpoints(endpoints: List<ApiEndpointDoc>): OpenApiSpec {

        logger.warn("OpenApiSpecBuilder::fromEndpoints : ${endpoints.count()} endpoints")

        val paths = mutableMapOf<String, JsonObject>()
        val tags = endpoints.flatMap { it.tags }.distinct().map {
            Tag(name = it, description = "")
        }

        for (endpoint in endpoints) {
            for (route in endpoint.routes) {

                val path = route.path
                // val methodMap = mutableMapOf<String, JsonObject>() // This variable is unused

                for (op in route.operations) {

                    val opJson = buildJsonObject {
                        put("summary", op.summary)
                        put("description", op.description)
                        // put("tags", op.tags.joinToString(prefix = "[\"", separator = "\", \"", postfix = "\"]"))
                        put("tags", JsonArray(op.tags.map { JsonPrimitive(it) })) // Corrected tags serialization
                        put("responses", buildJsonObject {
                            for ((code, resp) in op.responses) {
                                put(code.toString(), buildJsonObject {
                                    put("description", resp.description)
                                    // Optionally include schema or examples if you expose them
                                })
                            }
                        })

                        op.requestBody?.let { requestBodyVal ->
                            put("requestBody", buildJsonObject {
                                put("description", requestBodyVal.description ?: "Request body")
                                put("required", requestBodyVal.required)
                                put("content", buildJsonObject {
                                    put("application/json", buildJsonObject {
                                        put("schema", requestBodyVal.content["application/json"]!!.schema.toJson())
                                    })
                                })
                            })
                        }

                    }

                    val existing = paths.getOrElse(path) { buildJsonObject { } }.toMutableMap()

                    existing[op.method.lowercase()] = opJson

                    paths[path] = JsonObject(existing)

                }
            }
        }

        return OpenApiSpec(
            paths = paths,
            tags = tags
        )
    }
}
//
//fun OpenApiSpec.Companion.fromEndpoints(endpoints: List<ApiEndpointDoc>): OpenApiSpec {
//
//    val paths = mutableMapOf<String, JsonObject>()
//    val tags = endpoints.flatMap { it.tags }.distinct().map {
//        OpenApiSpec.Tag(name = it, description = "")
//    }
//
//    for (endpoint in endpoints) {
//        for (route in endpoint.routes) {
//            val path = route.path
//            val methodMap = mutableMapOf<String, JsonObject>()
//
//            for (op in route.operations) {
//                val opJson = buildJsonObject {
//                    put("summary", op.summary)
//                    put("description", op.description)
//                    put("tags", op.tags.joinToString(prefix = "[\"", separator = "\", \"", postfix = "\"]"))
//                    put("responses", buildJsonObject {
//                        for ((code, resp) in op.responses) {
//                            put(code.toString(), buildJsonObject {
//                                put("description", resp.description)
//                                // Optionally include schema or examples if you expose them
//                            })
//                        }
//                    })
//                    op.requestBody?.let {
//                        put("requestBody", buildJsonObject {
//                            put("description", "Request body")
//                            put("required", true)
//                            put("content", buildJsonObject {
//                                put("application/json", buildJsonObject {
//                                    put("schema", it.toJson()) // you'd implement toJson()
//                                })
//                            })
//                        })
//                    }
//                }
//
//                val existing = paths.getOrElse(path) { buildJsonObject { } }.toMutableMap()
//                existing[op.method.lowercase()] = opJson
//                paths[path] = JsonObject(existing)
//            }
//        }
//    }
//
//    return OpenApiSpec(
//        info = OpenApiSpec.Info(
//            title = "Generated API",
//            version = "1.0.0",
//            description = "Generated from @OwleApiSpec annotations"
//        ),
//        paths = paths,
//        tags = tags
//    )
//}
//
