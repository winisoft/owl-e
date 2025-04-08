package com.wini.owle.api

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json


abstract class BaseEndpointImpl<ID: Any, T: BaseDto>(
    private val dll: DLL<ID, T>,
    val dtoSerializer: KSerializer<T>,
    val dtoListSerializer: KSerializer<List<T>>,
    private val validator: (T) -> Boolean
): RoutedApi {

    suspend fun ApplicationCall.asyncResultUnit(
        result: AsyncResult<*>
    ) {
        when (result) {
            is AsyncResult.Success -> respond(HttpStatusCode.OK, Unit)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    suspend fun ApplicationCall.asyncResultSingle(
        result: AsyncResult<T>,
        isCreate: Boolean = false
    ) {
        when (result) {
            is AsyncResult.Success -> respondText(
                contentType = ContentType.Application.Json,
                status = if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK,
                text = Json.encodeToString(dtoSerializer, result.data)
            )
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    suspend fun ApplicationCall.asyncResultList(
        result: AsyncResult<List<T>>,
        isCreate: Boolean = false
    ) {
        when (result) {
            is AsyncResult.Success -> respondText(
                contentType = ContentType.Application.Json,
                status = if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK,
                text = Json.encodeToString(dtoListSerializer, result.data)
            )
            is AsyncResult.Failure -> respond(
                status = result.error.httpStatusCode,
                message = result.error
            )
        }
    }

    suspend fun ApplicationCall.create(
        validator: (T) -> Boolean = { true }
    ): AsyncResult<T> {
        return receiveObjectSafely(dtoSerializer)
            .let { it ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .run { takeIf(validator) ?: return AsyncError.ApiRequest.DtoConstraintError.asFailure() }
            .run(dll::create)
    }

    suspend fun ApplicationCall.createBatch(
        validator: (T) -> Boolean = { true }
    ): AsyncResult<List<T>> {
        return receiveObjectListSafely(dtoSerializer)
            .let { it ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .run { takeIf { all(validator) } ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .run(dll::createAll)
    }

    suspend fun ApplicationCall.update(
        validator: (T) -> Boolean = { true }
    ): AsyncResult<T> {
        return receiveObjectSafely(dtoSerializer)
            .let { it ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .run { takeIf(validator) ?: return AsyncError.ApiRequest.DtoConstraintError.asFailure() }
            .run(dll::update)
    }

    suspend fun ApplicationCall.updateBatch(
        validator: (T) -> Boolean = { true }
    ): AsyncResult<List<T>> {
        return receiveObjectListSafely(dtoSerializer)
            .let { it ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .run { takeIf { all(validator) } ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .run(dll::updateAll)
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