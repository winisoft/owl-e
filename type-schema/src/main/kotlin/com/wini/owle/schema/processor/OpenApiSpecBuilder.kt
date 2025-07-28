package com.wini.owle.ksp.processor

import com.wini.owle.ksp.doc.oas.ApiEndpointDoc
import com.wini.owle.ksp.doc.oas.OpenApiSpec
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.put
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator


object OpenApiSpecBuilder {

    fun fromEndpoints(endpoints: List<ApiEndpointDoc>): OpenApiSpec {

        val paths = mutableMapOf<String, JsonObject>()
        val tags = endpoints.flatMap { it.tags }.distinct().map {
            OpenApiSpec.Tag(name = it, description = "")
        }

        for (endpoint in endpoints) {
            for (route in endpoint.routes) {
                val path = route.path
                val methodMap = mutableMapOf<String, JsonObject>()

                for (op in route.operations) {
                    val opJson = buildJsonObject {
                        put("summary", op.summary)
                        put("description", op.description)
                        put("tags", op.tags.joinToString(prefix = "[\"", separator = "\", \"", postfix = "\"]"))
                        put("responses", buildJsonObject {
                            for ((code, resp) in op.responses) {
                                put(code.toString(), buildJsonObject {
                                    put("description", resp.description)
                                    // Optionally include schema or examples if you expose them
                                })
                            }
                        })
                        op.requestBody?.let {
                            put("requestBody", buildJsonObject {
                                put("description", "Request body")
                                put("required", true)
                                put("content", buildJsonObject {
                                    put("application/json", buildJsonObject {
                                        put("schema", it.toJson()) // you'd implement toJson()
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
            info = OpenApiSpec.Info(
                title = "Generated API",
                version = "1.0.0",
                description = "Generated from @OwleApiSpec annotations"
            ),
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
