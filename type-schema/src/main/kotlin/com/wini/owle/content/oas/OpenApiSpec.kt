package com.wini.owle.ksp.doc.oas

import com.wini.owle.ksp.doc.oas.info.Info
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Represents a complete OpenAPI specification.
 */
@Serializable
data class OpenApiSpec(
    val openapi: String = "3.0.0",
    val info: Info = Info(),
    val paths: Map<String, JsonObject>,
    val components: Map<String, JsonObject> = emptyMap(),
    val tags: List<OasTag> = emptyList()
) {


//    val oas: OpenAPI = OpenAPI(SpecVersion.V30).apply {
//
//        val oas = ResKeys.api.oas
//
//        info = Info().apply {
//            title = ResKeys..< oas.titleKey
//            version = ResKeys..< oas.versionKey
//            description = ResKeys..< oas.descriptionKey
//
//            contact = Contact().apply {
//                name = ResKeys..< oas.contact.nameKey
//            }
//
//            license = License().apply {
//                name = ResKeys..< oas.license.nameKey
//                url = ResKeys..< oas.license.urlKey
//            }
//
//            Server().apply {
//                url = ResKeys..< oas.server.url
//                addServersItem(this)
//            }
//
//        }
//    }
}



