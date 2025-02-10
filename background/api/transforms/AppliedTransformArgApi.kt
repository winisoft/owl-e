package com.wini.owle.api.transforms

import com.wini.owle.async.AsyncResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.wini.owle.dto.AppliedTransformArgDto
import com.wini.owle.services.transforms.AppliedTransformArgService


fun Application.AppliedTransformArgApi(service: AppliedTransformArgService) {

    routing {

        route("/transforms/applied-transform-args") {
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