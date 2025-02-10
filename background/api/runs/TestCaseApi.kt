package com.wini.owle.api.runs


import com.wini.owle.async.AsyncResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.wini.owle.dto.TestCaseDto
import com.wini.owle.services.runs.TestCaseService


fun Application.TestCaseApi(service: TestCaseService) {

    routing {

        route("/runs/test-cases") {

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
