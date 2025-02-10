package com.wini.owle.api.workflows

import com.wini.owle.dto.DataPointDto
import com.wini.owle.async.AsyncResult
import com.wini.owle.services.workflows.DataPointService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


fun Application.dataPointApi(dataPointService: DataPointService) {
    routing {
        route("/api/datapoints") {
            // Retrieve all DataPoints
            get {
                when (val result = dataPointService.getAll()) {
                    is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                    is AsyncResult.Failure -> handleFailure(result, call)
                }
            }

            // Retrieve a DataPoint by ID
            get("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                } else {
                    when (val result = dataPointService.getById(id)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Create a single DataPoint
            post {
                val dto = kotlin.runCatching { call.receiveNullable<DataPointDto>() }.getOrNull()
                if (dto == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid DataPoint data")
                } else {
                    when (val result = dataPointService.create(dto)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.Created, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Create multiple DataPoints
            post("/batch") {
                val dtos = kotlin.runCatching { call.receiveNullable<List<DataPointDto>>() }.getOrNull()
                if (dtos == null || dtos.isEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or empty DataPoint list")
                } else {
                    when (val result = dataPointService.create(dtos)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.Created, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Update a single DataPoint
            put {
                val dto = runCatching { call.receiveNullable<DataPointDto>() }.getOrNull()
                if (dto == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid DataPoint data")
                } else {
                    when (val result = dataPointService.update(dto)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Update multiple DataPoints
            put("/batch") {
                val dtos = runCatching { call.receiveNullable<List<DataPointDto>>() }.getOrNull()
                if (dtos.isNullOrEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or empty DataPoint list")
                } else {
                    when (val result = dataPointService.update(dtos)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Delete a single DataPoint by ID
            delete("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                } else {
                    when (val result = dataPointService.delete(id)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Delete multiple DataPoints by IDs
            delete("/batch") {
                val ids = runCatching { call.receiveNullable<List<Long>>() }.getOrNull()
                if (ids.isNullOrEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or empty ID list")
                } else {
                    when (val result = dataPointService.delete(ids)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }
        }
    }
}

private suspend fun handleFailure(result: AsyncResult.Failure, call: ApplicationCall) {
    val error = Json.encodeToString(result)
    when (result.errorName) {
        "NetworkError" -> call.respond(HttpStatusCode.ServiceUnavailable, error)
        "AuthenticationError" -> call.respond(HttpStatusCode.Unauthorized, error)
        "AuthorizationError" -> call.respond(HttpStatusCode.Forbidden, error)
        "ValidationError" -> call.respond(HttpStatusCode.BadRequest, error)
        "DatabaseError" -> call.respond(HttpStatusCode.InternalServerError, error)
        else -> call.respond(HttpStatusCode.InternalServerError, "Unknown error")
    }
}