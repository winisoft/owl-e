package com.wini.owle.api

import com.wini.owle.async.AsyncError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.routing.*
import io.ktor.util.reflect.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.starProjectedType
//
//
//inline fun <reified DTO : BaseDto> Route.sharedCrudApi(service: IService<DTO>) {
//
//    // Retrieve all entities
//    get {
//        when (val result = service.getAll()) {
//            is AsyncResult.Success -> {
//                val serializedData = Json.encodeToString(ListSerializer(DTO::class.serializer()), result.data)
//                call.respond(HttpStatusCode.OK, serializedData)
//            }
//            is AsyncResult.Failure -> handleFailure(result.error, call)
//        }
//    }
//
//
//    // Create a single entity
//    post {
//        val dto = runCatching { call.receiveNullable<DTO>() }.getOrNull()
//        if (dto == null) {
//            call.respond(HttpStatusCode.BadRequest, "Invalid data")
//        } else {
//            when (val result = service.create(dto)) {
//                is AsyncResult.Success -> call.respond(HttpStatusCode.Created, Json.encodeToString(result.data))
//                is AsyncResult.Failure -> handleFailure(result, call)
//            }
//        }
//    }
//
//    // Create multiple entities
//    post("/batch") {
//        val dtos = runCatching {
//            call.receiveNullable<List<DTO>>(TypeInfo(List::class))
//        }.getOrNull()
//        if (dtos.isNullOrEmpty()) {
//            call.respond(HttpStatusCode.BadRequest, TypeInfo(Nothing::class))
//        } else {
//            when (val result = service.create(dtos)) {
//                is AsyncResult.Success -> call.respond(HttpStatusCode.Created, TypeInfo(String::class))
//                is AsyncResult.Failure ->  handleFailure(Json.decodeFromString(result.errorBundle), call)
//            }
//        }
//    }
//
//    // Update a single entity
//    put {
//        val dto = runCatching { call.receiveNullable<DTO>() }.getOrNull()
//        if (dto == null) {
//            call.respond(HttpStatusCode.BadRequest, "Invalid data")
//        } else {
//            when (val result = service.update(dto)) {
//                is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
//                is AsyncResult.Failure -> handleFailure(result, call)
//            }
//        }
//    }
//
//    // Update multiple entities
//    put("/batch") {
//        val dtos = runCatching { call.receiveNullable<List<DTO>>() }.getOrNull()
//        if (dtos.isNullOrEmpty()) {
//            call.respond(HttpStatusCode.BadRequest, "Invalid or empty data list")
//        } else {
//            when (val result = service.update(dtos)) {
//                is AsyncResult.Success -> call.respond(HttpStatusCode.OK, Json.encodeToString(result.data))
//                is AsyncResult.Failure -> handleFailure(result, call)
//            }
//        }
//    }
//}

fun interpretHttpStatus(error: AsyncError): HttpStatusCode {
   return when (error) {
        is AsyncError.DatabaseError -> HttpStatusCode.InternalServerError
        is AsyncError.AuthenticationError -> HttpStatusCode.Unauthorized
        is AsyncError.AuthorizationError -> HttpStatusCode.Forbidden
        is AsyncError.NetworkError -> HttpStatusCode.ServiceUnavailable
        is AsyncError.ServiceLayerError -> HttpStatusCode.InternalServerError
        is AsyncError.ValidationError -> HttpStatusCode.BadRequest
        else -> HttpStatusCode.InternalServerError
    }
}

suspend fun handleFailure(error: AsyncError, call: ApplicationCall) {

    (interpretHttpStatus(error) to Json.encodeToString(AsyncError.serializer(), error)).let { (statusCode, errorMessage) ->

        call.respond(statusCode, TypeInfo(type=AsyncError::class, kotlinType=error::class.starProjectedType))
    }

}
