package com.wini.owle.api.workflows

import com.wini.owle.async.AsyncResult
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json



fun Application.WorkflowFolderApi(service: WorkflowFolderEntity) {

    routing {

        route("/workflows/folders") {

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