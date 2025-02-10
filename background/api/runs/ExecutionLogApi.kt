package com.wini.owle.api.runs


import com.wini.owle.async.AsyncResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.wini.owle.dto.ExecutionLogDto
import com.wini.owle.services.runs.ExecutionLogService


fun Application.ExecutionLogApi(service: ExecutionLogService) {

    routing {

        route("/runs/execution-logs") {

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
