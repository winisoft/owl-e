package com.wini.owle.api.environs

import com.wini.owle.dto.PackageDto
import com.wini.owle.async.AsyncResult
import com.wini.owle.services.PackageService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


fun Application.packageApi(packageService: PackageService) {

    routing {

        route("/api/packages") {

            // Retrieve all Packages
            get {
                when (val result = packageService.getAll()) {
                    is AsyncResult.Success<*> -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                    is AsyncResult.Failure -> handleFailure(result, call)
                }
            }

            // Retrieve a Package by ID
            get("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                } else {
                    when (val result = packageService.getById(id)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Create a single Package
            post {
                val dto = kotlin.runCatching { call.receiveNullable<PackageDto>() }.getOrNull()
                if (dto == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid Package data")
                } else {
                    when (val result = packageService.create(dto)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.Created, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Create multiple Packages
            post("/batch") {
                val dtos = kotlin.runCatching { call.receiveNullable<List<PackageDto>>() }.getOrNull()
                if (dtos == null || dtos.isEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or empty Package list")
                } else {
                    when (val result = packageService.create(dtos)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.Created, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Update a single Package
            put {
                val dto = runCatching { call.receiveNullable<PackageDto>() }.getOrNull()
                if (dto == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid Package data")
                } else {
                    when (val result = packageService.update(dto)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Update multiple Packages
            put("/batch") {
                val dtos = runCatching { call.receiveNullable<List<PackageDto>>() }.getOrNull()
                if (dtos.isNullOrEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or empty Package list")
                } else {
                    when (val result = packageService.update(dtos)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Delete a single Package by ID
            delete("/{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
                } else {
                    when (val result = packageService.delete(id)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }

            // Delete multiple Packages by IDs
            delete("/batch") {
                val ids = runCatching { call.receiveNullable<List<Long>>() }.getOrNull()
                if (ids.isNullOrEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or empty ID list")
                } else {
                    when (val result = packageService.delete(ids)) {
                        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
                        is AsyncResult.Failure -> handleFailure(result, call)
                    }
                }
            }
        }
    }
}

private suspend fun handleFailure(result: AsyncResult.Failure, call: ApplicationCall) {

}

//private suspend fun handleFailure(result: AsyncResult.Failure, call: ApplicationCall) {
//    val error = Json.encodeToString(result)
//    when (result.errorName) {
//        "NetworkError" -> call.respond(HttpStatusCode.ServiceUnavailable, error)
//        "AuthenticationError" -> call.respond(HttpStatusCode.Unauthorized, error)
//        "AuthorizationError" -> call.respond(HttpStatusCode.Forbidden, error)
//        "ValidationError" -> call.respond(HttpStatusCode.BadRequest, error)
//        "DatabaseError" -> call.respond(HttpStatusCode.InternalServerError, error)
//        else -> call.respond(HttpStatusCode.InternalServerError, "Unknown error")
//    }
//}