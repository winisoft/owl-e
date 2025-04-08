package com.wini.owle.api

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncError.ApiRequest
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
            is AsyncResult.Success -> respond(HttpStatusCode.NoContent)
            is AsyncResult.Failure -> respond(result.error.toHttpStatus(), result.error)
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
            is AsyncResult.Failure -> respond(result.error.toHttpStatus(), result.error)
        }
    }

    suspend fun ApplicationCall.asyncResultList(
        result: AsyncResult<List<T>>,
        isCreate: Boolean = false
    ) {
        when (result) {
            is AsyncResult.Success -> {
                val apiResponse = ApiResponse(
                    success = true,
                    data = result.data,
                )
                respondText(
                    contentType = ContentType.Application.Json,
                    status = if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK,
                    text = Json.encodeToString(dtoListSerializer, result.data)
                )
            }
            is AsyncResult.Failure -> {
                respond(
                    status = result.error.toHttpStatus(),
                    message = ApiResponse<T>(
                        success = false,
                        message = result.error.toApiResponseMessage(),
                        errors = result.error.toApiErrors()
                    )
                )
            }
        }
    }

    suspend fun ApplicationCall.create(
        validator: (T) -> Boolean = { true }
    ): AsyncResult<T> {
        return receiveObjectSafely(dtoSerializer).run {
            when {
                this == null -> return ApiRequest.MalformedBodyData.asFailure()
                !validator(this) -> return ApiRequest.ConstraintViolationData.asFailure()
                else -> dll.create(this)
            }
        }
    }

    suspend fun ApplicationCall.createBatch(
        validator: (T) -> Boolean = { true }
    ): AsyncResult<List<T>> {
        return receiveObjectListSafely(dtoSerializer).run {
            when {
                isNullOrEmpty() -> return ApiRequest.MalformedBodyData.asFailure()
                any { !validator(it) } -> return ApiRequest.ConstraintViolationData.asFailure()
                else -> dll.createAll(this)
            }
        }
    }

    suspend fun ApplicationCall.update(
        validator: (T) -> Boolean = { true }
    ): AsyncResult<T> {
        return receiveObjectSafely(dtoSerializer).run {
            when {
                this == null -> return ApiRequest.MalformedBodyData.asFailure()
                !validator(this) -> return ApiRequest.ConstraintViolationData.asFailure()
                else -> dll.update(this)
            }
        }
    }

    suspend fun ApplicationCall.updateBatch(
        validator: (T) -> Boolean = { true }
    ): AsyncResult<List<T>> {
        return receiveObjectListSafely(dtoSerializer).run {
            when {
                isNullOrEmpty() -> return ApiRequest.MalformedBodyData.asFailure()
                any { !validator(it) } -> return ApiRequest.ConstraintViolationData.asFailure()
                else -> dll.createAll(this)
            }
        }
    }

    fun getAll(): AsyncResult<List<T>> {
        return dll.getAll()
    }

    private suspend fun <T> ApplicationCall.receiveObjectSafely(serializer: KSerializer<T>): T? =
        runCatching { Json.decodeFromString(serializer, receiveText()) }.getOrNull()

    private suspend fun <T> ApplicationCall.receiveObjectListSafely(serializer: KSerializer<T>): List<T>? =
        runCatching { Json.decodeFromString(ListSerializer(serializer), receiveText()) }.getOrNull()


    fun ApplicationCall.toApiResponse(result: AsyncResult<T>): Pair<HttpStatusCode, ApiResponse<T>> {
        return when (result) {
            is AsyncResult.Success<T> -> {
                request.httpMethod.toHttpStatusOnSuccess() to ApiResponse(
                    success = true,
                    data = if (request.httpMethod == HttpMethod.Delete) null else result.data
                )
            }
            is AsyncResult.Failure -> {
                result.error.toHttpStatus() to ApiResponse<T>(
                    success = false,
                    message = result.error.toApiResponseMessage(),
                    errors = result.error.toApiErrors()
                )
            }
        }
    }

    fun AsyncError.toApiResponseMessage() : String {
        return when (this) {
            is AsyncError.ApplicationError,
            is AsyncError.ServiceLayerError,
            is ApiRequest.InvalidLongId,
            is AsyncError.AuthenticationError,
            is AsyncError.AuthorizationError,
            is ApiRequest.ConstraintViolationData,
            is ApiRequest.InvalidCompositeIdElement,
            is ApiRequest.MalformedBodyData,
            is ApiRequest.MissingCompositeIdElement,
            is ApiRequest.MissingId,
            is AsyncError.DbTransactionError,
            is AsyncError.DatabaseError,
            is AsyncError.NoSuchIdError -> "Simple summary of the meaning of the constituent erros taken altogether, e.g. 'Some deletions failed' or 'No valid data provided'."

            is ApiRequest.InvalidField -> TODO()
            is ApiRequest.MissingField -> TODO()
            is ApiRequest.UnexpectedField -> TODO()
            is AsyncError.ExternalDependencyError.HttpFailure -> TODO()
            is AsyncError.ExternalDependencyError.Timeout -> TODO()
            is AsyncError.ExternalDependencyError.UnexpectedResponse -> TODO()
            is AsyncError.InvalidStateTransition -> TODO()
            is AsyncError.InvariantViolation -> TODO()
            is AsyncError.PartialFailure<*> -> TODO()
            is AsyncError.ValidationError -> TODO()
        }
    }

    fun AsyncError.toApiErrorMessage(): String {
        return when (this) {
            is AsyncError.ApplicationError,
            is AsyncError.ServiceLayerError,
            is ApiRequest.InvalidLongId,
            is AsyncError.AuthenticationError,
            is AsyncError.AuthorizationError,
            is ApiRequest.ConstraintViolationData,
            is ApiRequest.InvalidCompositeIdElement,
            is ApiRequest.MalformedBodyData,
            is ApiRequest.MissingCompositeIdElement,
            is ApiRequest.MissingId,
            is AsyncError.DbTransactionError,
            is AsyncError.DatabaseError,
            is AsyncError.NoSuchIdError -> "Simple summary of the specific error condition, e.g. 'ID list cannot be empty.' or 'ID 789 not found.'"

            is ApiRequest.InvalidField -> TODO()
            is ApiRequest.MissingField -> TODO()
            is ApiRequest.UnexpectedField -> TODO()
            is AsyncError.ExternalDependencyError.HttpFailure -> TODO()
            is AsyncError.ExternalDependencyError.Timeout -> TODO()
            is AsyncError.ExternalDependencyError.UnexpectedResponse -> TODO()
            is AsyncError.InvalidStateTransition -> TODO()
            is AsyncError.InvariantViolation -> TODO()
            is AsyncError.PartialFailure<*> -> TODO()
            is AsyncError.ValidationError -> TODO()
        }
    }

    /**
     * Determines the appropriate HTTP status to communicate successful execution of the request to the user
     */
    private fun HttpMethod.toHttpStatusOnSuccess(): HttpStatusCode = when (this) {
        HttpMethod.Get,
        HttpMethod.Put -> HttpStatusCode.OK
        HttpMethod.Post -> HttpStatusCode.Created
        HttpMethod.Delete -> HttpStatusCode.NoContent
        else -> throw IllegalStateException("Unsupported HTTP method: $value")
    }

    /**
     * Determines the appropriate 'high-level' articulation of the error and the appropriate status to associate with it
     */
    fun AsyncError.toHttpStatus(): HttpStatusCode = when (this) {
        is AsyncError.ApplicationError,
        is ApiRequest.ConstraintViolationData,
        is ApiRequest.InvalidCompositeIdElement,
        is ApiRequest.MalformedBodyData,
        is ApiRequest.MissingCompositeIdElement,
        is ApiRequest.MissingId,
        is AsyncError.NoSuchIdError,
        is AsyncError.DatabaseError,
        is ApiRequest.InvalidLongId-> HttpStatusCode.BadRequest
        is AsyncError.AuthenticationError -> HttpStatusCode.Unauthorized
        is AsyncError.AuthorizationError -> HttpStatusCode.Forbidden
        is AsyncError.DbTransactionError -> HttpStatusCode.InternalServerError
        is AsyncError.ServiceLayerError -> HttpStatusCode.ServiceUnavailable
        is ApiRequest.InvalidField -> TODO()
        is ApiRequest.MissingField -> TODO()
        is ApiRequest.UnexpectedField -> TODO()
        is AsyncError.ExternalDependencyError.HttpFailure -> TODO()
        is AsyncError.ExternalDependencyError.Timeout -> TODO()
        is AsyncError.ExternalDependencyError.UnexpectedResponse -> TODO()
        is AsyncError.InvalidStateTransition -> TODO()
        is AsyncError.InvariantViolation -> TODO()
        is AsyncError.PartialFailure<*> -> TODO()
        is AsyncError.ValidationError -> TODO()
    }

    /**
     * Utilizes the data collected at the site of the error to build a list of errors contributing to the ApiResponse's
     * general but conclusive articulated failure.
     */
    fun AsyncError.toApiErrors(): List<ApiError> {
        return listOf(
            ApiError(
                code = errorCode,
                message = when (this) {
                    is ApiRequest.MissingId -> "The expected primary key 'id' was not provided."
                    is ApiRequest.InvalidLongId -> "The expected primary key 'id' was provided in an invalid format."
                    is AsyncError.NoSuchIdError -> "The specified ID: [$id] does not exist."
                    is ApiRequest.MalformedBodyData ->  "Illegal request body data."
                    is ApiRequest.ConstraintViolationData -> "The request body data violated constraints on its permissible values"
                    is ApiRequest.MissingCompositeIdElement -> "A required composite ID element was not provided"
                    is ApiRequest.InvalidCompositeIdElement -> "A required composite ID element was provided in an invalid format."
                    is AsyncError.ApplicationError -> "The application suffered a catastrophic failure."
                    is AsyncError.AuthenticationError -> "The user is unauthenticated."
                    is AsyncError.AuthorizationError -> "The user is not authorized to take the specified action."
                    is AsyncError.DbTransactionError -> "The database transaction failed."
                    is AsyncError.ServiceLayerError -> "The server was not ready to handle the request."
                    is AsyncError.DatabaseError -> "The database failed to perform the operation."
                    is ApiRequest.InvalidField -> TODO()
                    is ApiRequest.MissingField -> TODO()
                    is ApiRequest.UnexpectedField -> TODO()
                    is AsyncError.ExternalDependencyError.HttpFailure -> TODO()
                    is AsyncError.ExternalDependencyError.Timeout -> TODO()
                    is AsyncError.ExternalDependencyError.UnexpectedResponse -> TODO()
                    is AsyncError.InvalidStateTransition -> TODO()
                    is AsyncError.InvariantViolation -> TODO()
                    is AsyncError.PartialFailure<*> -> TODO()
                    is AsyncError.ValidationError -> TODO()
                }
            )
        )
    }
}