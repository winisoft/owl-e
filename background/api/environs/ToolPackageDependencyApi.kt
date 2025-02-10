package com.wini.owle.api.environs

import com.wini.owle.async.AsyncResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.wini.owle.dto.ToolPackageDependencyDto
import com.wini.owle.services.environs.ToolPackageDependencyService


fun Application.ToolPackageDependencyApi(service: ToolPackageDependencyService) {

    routing {

        route("/environs/tool-package-dependencies") {

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