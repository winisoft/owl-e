package com.wini.owle.api

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


// Helper function to handle different AsyncResult outcomes
//suspend fun <T: Any> respondWithResult(call: ApplicationCall, result: AsyncResult<T>) {
//    when (result) {
//        is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result))
//        is AsyncResult.Failure -> {
//            val errorResponse = mapErrorToHttpStatus(result)
//            call.respondText(
//                text = Json.encodeToString(result),
//                contentType = ContentType.Application.Json,
//                status = errorResponse.first
//            )
//        }
//    }
//}

// Maps different errors to HTTP status codes and provides messages
//fun mapErrorToHttpStatus(result: AsyncResult.Failure): Pair<HttpStatusCode, String> {
//    return when (result.getError<AsyncError>()) {
//        is AsyncError.NetworkError -> HttpStatusCode.ServiceUnavailable to result.errorMessage("Network error")
//        is AsyncError.AuthenticationError -> HttpStatusCode.Unauthorized to result.errorMessage("Authentication error")
//        is AsyncError.AuthorizationError -> HttpStatusCode.Forbidden to result.errorMessage("Authorization error")
//        is AsyncError.ValidationError -> HttpStatusCode.BadRequest to result.errorMessage("Validation error")
//        is AsyncError.DatabaseError -> HttpStatusCode.InternalServerError to result.errorMessage("Database error")
//        else -> HttpStatusCode.InternalServerError to result.errorMessage()
//    }
//}