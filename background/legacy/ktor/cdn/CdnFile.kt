package com.wini.owle.air.ktor.cdn

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

//import io.ktor.server.application.*
//import io.ktor.http.*
//import io.ktor.response.*
//import io.ktor.routing.*
//import io.ktor.util.pipeline.*

interface CdnFile {

    val fileName: String

    val resource: String?

    val contentType: ContentType

    val sourceDirectory: String

    val serverPath: String
        get() = "/$fileName"

    val path: String
        get() = "$sourceDirectory/$fileName"

    fun Routing.declareRoute(): Route {
        return get(serverPath) { provide() }
    }

    fun ClassLoader.loadResource(): String? {
        getResourceAsStream(path)?.use {
            it.bufferedReader().use { reader ->
                return reader.readText()
            }
        }
        return null
    }

    suspend fun PipelineContext<Unit, ApplicationCall>.provide(): suspend (ApplicationCall) -> ApplicationCall = {
        it.apply { respondText(text = resource!!, contentType = contentType) }
    }
}