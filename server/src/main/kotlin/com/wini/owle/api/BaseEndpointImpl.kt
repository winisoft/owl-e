package com.wini.owle.api

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncError.ApiRequest
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
    open val validator: (T) -> Boolean
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
                ApiRequest.MissingParameter,
                ApiRequest.ParamValueMalformed,
                ApiRequest.RequestBodyMalformed,
                ApiRequest.EmptyListNotAllowed,
                AsyncError.MultipleParamFailure,
                ApiRequest.TypeDefinitionValidation
            ) ->
                HttpStatusCode.BadRequest
            else ->
                HttpStatusCode.InternalServerError
        }

        val message = failure.error.message
        val asyncErrors = if (failure.error is AsyncError.MultipleParamFailure)
            listOf((failure.error as AsyncError.MultipleParamFailure).failed)
        else
            listOf(failure.error)

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
        crossinline validator: (T) -> Boolean
    ): AsyncResult<T> {
        return runCatching {
            Json.decodeFromString(dtoSerializer, receiveText())
        }.getOrElse {
            return ApiRequest.RequestBodyMalformed.asFailure()
        }.takeIf {
            validator(it)
        }.ifNullExit {
            return ApiRequest.TypeDefinitionValidation.asFailure()
        }.let {
            AsyncResult.Success(it)
        }
    }

    protected suspend inline fun ApplicationCall.readDtos(
        dtoListSerializer: KSerializer<List<T>>,
        crossinline validator: (T) -> Boolean
    ): AsyncResult<List<T>> {
        return runCatching {
            Json.decodeFromString(dtoListSerializer, receiveText())
        }.getOrElse {
            return ApiRequest.RequestBodyMalformed.asFailure()
        }.takeIf {
            it.all(validator)
        }.ifNullExit {
            return ApiRequest.TypeDefinitionValidation.asFailure()
        }.let {
            AsyncResult.Success(it)
        }
    }

    private val responseOneSerializer: KSerializer<ApiResponse<T>> = ApiResponse.serializer(dtoSerializer)
    private val responseManySerializer: KSerializer<ApiResponse<List<T>>> = ApiResponse.serializer(dtoListSerializer)
    private val responseNoneSerializer: KSerializer<ApiResponse<Unit>> = ApiResponse.serializer(Unit.serializer())


}