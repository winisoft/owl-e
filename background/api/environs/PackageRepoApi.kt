package com.wini.owle.api.environs

import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.PackageRepoDto
import com.wini.owle.services.PackageRepoService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


fun Application.packageRepoApi(packageRepoService: PackageRepoService) {
    routing {
        route("/api/package-repos") {
            // Retrieve all PackageRepos
            get {
                when (val result = packageRepoService.getAll()) {
                    is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                    is AsyncResult.Failure -> handleFailure(result, call)
                }
            }

            // Retrieve a PackageRepo by ID
            get("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                } else {
                    when (val result = packageRepoService.getById(id)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Create a single PackageRepo
            post {
                val dto = kotlin.runCatching { call.receiveNullable<PackageRepoDto>() }.getOrNull()
                if (dto == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid PackageRepo data")
                } else {
                    when (val result = packageRepoService.create(dto)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.Created, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Create multiple PackageRepos
            post("/batch") {
                val dtos = kotlin.runCatching { call.receiveNullable<List<PackageRepoDto>>() }.getOrNull()
                if (dtos == null || dtos.isEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or empty PackageRepo list")
                } else {
                    when (val result = packageRepoService.create(dtos)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.Created, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Update a single PackageRepo
            put {
                val dto = runCatching { call.receiveNullable<PackageRepoDto>() }.getOrNull()
                if (dto == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid PackageRepo data")
                } else {
                    when (val result = packageRepoService.update(dto)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Update multiple PackageRepos
            put("/batch") {
                val dtos = runCatching { call.receiveNullable<List<PackageRepoDto>>() }.getOrNull()
                if (dtos.isNullOrEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or empty PackageRepo list")
                } else {
                    when (val result = packageRepoService.update(dtos)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Delete a single PackageRepo by ID
            delete("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                } else {
                    when (val result = packageRepoService.delete(id)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Delete multiple PackageRepos by IDs
            delete("/batch") {
                val ids = runCatching { call.receiveNullable<List<Long>>() }.getOrNull()
                if (ids.isNullOrEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or empty ID list")
                } else {
                    when (val result = packageRepoService.delete(ids)) {
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
