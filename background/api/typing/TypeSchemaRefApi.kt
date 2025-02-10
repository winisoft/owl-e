package com.wini.owle.api.typing

import com.wini.owle.async.AsyncResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.wini.owle.dto.TypeSchemaRefDto
import com.wini.owle.services.typing.TypeSchemaRefService


fun Application.TypeSchemaRefApi(service: TypeSchemaRefService) {

    routing {

        route("/typing/type-schema-refs") {

            get {

            }

            get("/{id}") {

            }

            post {

            }

            post("/batch") {

            }

            put {

            }

            put("/batch") {

            }

            delete("/{id}") {

            }

            delete("/batch") {

            }
        }
    }
}
