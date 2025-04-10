package com.wini.owle.api

import com.wini.owle.validators.DtoValidator
import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncError.ApiRequestError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.ifNullExit
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json


abstract class BaseEndpointImpl<ID: Any, T: BaseDto>(
    open val dll: DLL<ID, T>,
    val dtoSerializer: KSerializer<T>,
    val dtoListSerializer: KSerializer<List<T>>,
    open val validator: DtoValidator<T>
): RoutedApi {

    abstract fun ApplicationCall.readId(): AsyncResult<ID>

    abstract suspend fun ApplicationCall.readIds(): AsyncResult<List<ID>>

    suspend fun ApplicationCall.execute(owleCall: OwleApiCall) {
        when (owleCall) {
            is OwleApiCall.GetMany ->
                methodCallRespond(owleCall, responseManySerializer) { dll.getAll() }
            is OwleApiCall.GetOne ->
                when (val idResult = readId()) {
                    is AsyncResult.Failure -> failureRespond(idResult, responseOneSerializer)
                    is AsyncResult.Success -> methodCallRespond(owleCall, responseOneSerializer) {
                        dll.getById(idResult.data)
                    }
                }
            is OwleApiCall.DeleteOne -> when (val idResult = readId()) {
                is AsyncResult.Failure -> failureRespond(idResult, responseNoneSerializer)
                is AsyncResult.Success -> methodCallRespond(owleCall, responseNoneSerializer) {
                    dll.delete(idResult.data)
                }
            }
            is OwleApiCall.CreateOne -> when (val dtoResult = readDto(dtoSerializer, validator)) {
                is AsyncResult.Failure -> failureRespond(dtoResult, responseOneSerializer)
                is AsyncResult.Success<T> -> methodCallRespond(owleCall, responseOneSerializer) {
                    dll.create(dtoResult.data)
                }
            }
            is OwleApiCall.UpdateOne -> when (val dtoResult = readDto(dtoSerializer, validator)) {
                is AsyncResult.Failure -> failureRespond(dtoResult, responseOneSerializer)
                is AsyncResult.Success -> methodCallRespond(owleCall, responseOneSerializer) {
                    dll.update(dtoResult.data)
                }
            }
            is OwleApiCall.DeleteMany -> when (val idsResult = readIds()) {
                is AsyncResult.Failure -> failureRespond(idsResult, responseNoneSerializer)
                is AsyncResult.Success -> methodCallRespond(owleCall, responseNoneSerializer) {
                    dll.deleteAll(idsResult.data)
                }
            }
            is OwleApiCall.CreateMany -> when (val dtosResult = readDtos(dtoListSerializer, validator)) {
                is AsyncResult.Failure -> failureRespond(dtosResult, responseManySerializer)
                is AsyncResult.Success -> methodCallRespond(owleCall, responseManySerializer) {
                    dll.createAll(dtosResult.data)
                }
            }
            is OwleApiCall.UpdateMany -> when (val dtosResult = readDtos(dtoListSerializer, validator)) {
                is AsyncResult.Failure -> failureRespond(dtosResult, responseManySerializer)
                is AsyncResult.Success -> methodCallRespond(owleCall, responseManySerializer) {
                    dll.updateAll(dtosResult.data)
                }
            }
        }
    }

    suspend fun <T> ApplicationCall.failureRespond(
        failure: AsyncResult.Failure,
        serializer: KSerializer<ApiResponse<T>>
    ) {
        val statusCode = when (failure.error) {
            in listOf(
                ApiRequestError.MissingParameter,
                ApiRequestError.ParamValueMalformedLong,
                ApiRequestError.RequestBodyMalformed,
                ApiRequestError.EmptyListNotAllowed,
                ApiRequestError.MultipleParamFailure,
                ApiRequestError.MultipleTypeValidations
            ) ->
                HttpStatusCode.BadRequest
            else ->
                HttpStatusCode.InternalServerError
        }

        val message = failure.error.message
        val asyncErrors = when (failure.error) {
            is ApiRequestError.MultipleTypeValidations ->
                (failure.error as ApiRequestError.MultipleTypeValidations).violations
            is ApiRequestError.MultipleParamFailure ->
                (failure.error as ApiRequestError.MultipleParamFailure).failed
            else ->
                listOf(failure.error)
        }


        val apiErrors = asyncErrors.map { ApiError.fromAsyncError(it as AsyncError) }
        val responseBody = Json.encodeToString(serializer, ApiResponse(
            success = false,
            message = message,
            errors = apiErrors
        ))

        respond(statusCode, responseBody)
    }

    suspend fun <T> ApplicationCall.methodCallRespond(
        owleCall: OwleApiCall,
        serializer: KSerializer<ApiResponse<T>>,
        resultFn: () -> AsyncResult<T>
    ){
        when (val result = resultFn()) {
            is AsyncResult.Failure -> failureRespond(result, serializer)
            is AsyncResult.Success -> Json.encodeToString(serializer,
                ApiResponse(
                    success = true,
                    data = if (owleCall.successStatus != HttpStatusCode.NoContent) result.data else null
                )
            ).apply { respond(owleCall.successStatus, this) }
        }
    }

    private suspend inline fun ApplicationCall.readDto(
        dtoSerializer: KSerializer<T>,
        validator: DtoValidator<T>
    ): AsyncResult<T> {
        return runCatching {
            Json.decodeFromString(dtoSerializer, receiveText())
        }.getOrElse {
            return ApiRequestError.RequestBodyMalformed.asFailure()
        }.run {
            this to validator.validate(this)
        }.let { (items, validationResult) ->
            if (validationResult.violations.isNotEmpty())
                validationResult.asFailure()
            else
                AsyncResult.Success(items)
        }
    }

    protected suspend inline fun ApplicationCall.readDtos(
        dtoListSerializer: KSerializer<List<T>>,
        validator: DtoValidator<T>
    ): AsyncResult<List<T>> {
        return runCatching {
            Json.decodeFromString(dtoListSerializer, receiveText())
        }.getOrElse {
            return ApiRequestError.RequestBodyMalformed.asFailure()
        }.takeIf { it.isNotEmpty() }.ifNullExit {
            return ApiRequestError.EmptyListNotAllowed.asFailure()
        }.run {
            this to validator.validateAll(this)
        }.let { (items, validationResult) ->
            if (validationResult.violations.isNotEmpty())
                validationResult.asFailure()
            else
                AsyncResult.Success(items)
        }
    }

    private val responseOneSerializer: KSerializer<ApiResponse<T>> = ApiResponse.serializer(dtoSerializer)
    private val responseManySerializer: KSerializer<ApiResponse<List<T>>> = ApiResponse.serializer(dtoListSerializer)
    private val responseNoneSerializer: KSerializer<ApiResponse<Unit>> = ApiResponse.serializer(Unit.serializer())
}