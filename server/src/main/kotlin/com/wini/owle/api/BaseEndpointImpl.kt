package com.wini.owle.api

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json


abstract class BaseEndpointImpl<ID: Any, T: BaseDto>(
    private val dll: DLL<ID, T>,
    val serializer: KSerializer<T>,
    val listSerializer: KSerializer<List<T>>,
    private val validator: (T) -> Boolean
): RoutedApi {

    suspend fun ApplicationCall.asyncResultSingle(
        result: AsyncResult<T>,
        isCreate: Boolean = false
    ) {
        when (result) {
            is AsyncResult.Success -> respondText(
                contentType = ContentType.Application.Json,
                status = if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK,
                text = Json.encodeToString(serializer, result.data)
            )
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    suspend fun ApplicationCall.asyncResultMany(
        result: AsyncResult<List<T>>,
        isCreate: Boolean = false
    ) {
        when (result) {
            is AsyncResult.Success -> respondText(
                contentType = ContentType.Application.Json,
                status = if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK,
                text = Json.encodeToString(listSerializer, result.data)
            )
            is AsyncResult.Failure -> respond(
                status = result.error.httpStatusCode,
                message = result.error
            )
        }
    }

    suspend fun ApplicationCall.asyncResultUnit(
        result: AsyncResult<T>,
        isCreate: Boolean = false
    ) {
        when (result) {
            is AsyncResult.Success -> respondText(
                contentType = ContentType.Application.Json,
                status = if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK,
                text = Json.encodeToString(serializer, result.data)
            )
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    suspend fun ApplicationCall.asyncResult(result: AsyncResult<Unit>) {
        when (result) {
            is AsyncResult.Success -> respond(HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    suspend fun create(
        call: ApplicationCall,
        validator: (T) -> Boolean = { true }
    ): AsyncResult<T> {
        return call.receiveObjectSafely(serializer)
            .let { it ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .run { takeIf(validator) ?: return AsyncError.ApiRequest.DtoConstraintError.asFailure() }
            .run(dll::create)
    }

    suspend fun createBatch(
        call: ApplicationCall,
        validator: (T) -> Boolean = { true }
    ): AsyncResult<List<T>> {
        return call.receiveObjectListSafely(serializer)
            .let { it ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .run { takeIf { all(validator) } ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .run(dll::createAll)
    }

    suspend fun update(
        call: ApplicationCall,
        validator: (T) -> Boolean = { true }
    ): AsyncResult<T> {
        return call.receiveObjectSafely(serializer)
            .let { it ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .run { takeIf(validator) ?: return AsyncError.ApiRequest.DtoConstraintError.asFailure() }
            .run(dll::update)
    }

    suspend fun updateBatch(
        call: ApplicationCall,
        validator: (T) -> Boolean = { true }
    ): AsyncResult<List<T>> {
        return call.receiveObjectListSafely(serializer)
            .let { it ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .run { takeIf { all(validator) } ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .run(dll::createAll)
    }

    fun getAll(): AsyncResult<List<T>> {
        return dll.getAll()
    }

    private suspend fun <T> ApplicationCall.receiveObjectSafely(serializer: KSerializer<T>): T? =
        runCatching { Json.decodeFromString(serializer, receiveText()) }.getOrNull()

    private suspend fun <T> ApplicationCall.receiveObjectListSafely(serializer: KSerializer<T>): List<T>? =
        runCatching { Json.decodeFromString(ListSerializer(serializer), receiveText()) }.getOrNull()

    val AsyncError.httpStatusCode: HttpStatusCode
        get() = when (this) {
            is AsyncError.Database,
            is AsyncError.ApplicationError,
            is AsyncError.ServiceLayerError -> HttpStatusCode.InternalServerError
            is AsyncError.ValidationError,
            is AsyncError.ApiRequest.CompositeIdError,
            is AsyncError.ApiRequest.DtoConstraintError,
            is AsyncError.ApiRequest.EmptyList,
            is AsyncError.ApiRequest.LongIdInvalid,
            is AsyncError.ApiRequest.SerializationError -> HttpStatusCode.BadRequest
            is AsyncError.NoSuchIdError -> HttpStatusCode.NotFound
            is AsyncError.AuthenticationError -> HttpStatusCode.Unauthorized
            is AsyncError.AuthorizationError -> HttpStatusCode.Forbidden
            is AsyncError.NetworkError -> HttpStatusCode.fromValue(statusCode)
        }
}