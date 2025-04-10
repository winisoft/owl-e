package com.wini.owle.api

import com.wini.owle.api.OwleApiCall.Companion.successStatus
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

    suspend fun ApplicationCall.execute(owleCall: OwleApiCall<T>) {

        when (owleCall) {
            is OwleApiCall.GetMany ->
                methodCallRespond(owleCall, owleCall.responseSerializer(dtoSerializer, dtoListSerializer, Unit.serializer())) {
                    dll.getAll()
                }
            is OwleApiCall.GetOne ->
                handle(readId(), owleCall) { dll.getById(it) }

            is OwleApiCall.DeleteOne ->
                handle(readId(), owleCall) { dll.delete(it) }

            is OwleApiCall.CreateOne ->
                handle(readDto(dtoSerializer, validator), owleCall) { dll.create(it) }

            is OwleApiCall.UpdateOne ->
                handle(readDto(dtoSerializer, validator), owleCall) { dll.update(it) }

            is OwleApiCall.DeleteMany ->
                handle(readIds(), owleCall) { dll.deleteAll(it) }

            is OwleApiCall.CreateMany ->
                handle(readDtos(dtoListSerializer, validator), owleCall) { dll.createAll(it) }

            is OwleApiCall.UpdateMany ->
                handle(readDtos(dtoListSerializer, validator), owleCall) { dll.updateAll(it) }
        }
    }

    private suspend fun <D> ApplicationCall.failureRespond(
        failure: AsyncResult.Failure,
        serializer: KSerializer<ApiResponse<D>>
    ) {
        val apiResponse = ApiResponse<D>(
            success = false,
            message = failure.error.message,
            errors = when (failure.error) {
                is ApiRequestError.MultipleTypeValidations ->
                    (failure.error as ApiRequestError.MultipleTypeValidations).violations
                is ApiRequestError.MultipleParamFailure ->
                    (failure.error as ApiRequestError.MultipleParamFailure).failed
                else ->
                    listOf(failure.error)
            }.map {
                ApiError.fromAsyncError(it)
            }
        )

        respond(failure.toStatusCode(), Json.encodeToString(serializer, apiResponse))
    }

    private fun AsyncResult.Failure.toStatusCode(): HttpStatusCode {
        return when (error) {
            is ApiRequestError.MissingParameter,
            is ApiRequestError.ParamValueMalformedLong,
            is ApiRequestError.RequestBodyMalformed,
            is ApiRequestError.EmptyListNotAllowed,
            is ApiRequestError.MultipleParamFailure,
            is ApiRequestError.MultipleTypeValidations ->
                HttpStatusCode.BadRequest
            is AsyncError.NoSuchIdError ->
                HttpStatusCode.NotFound
            else ->
                HttpStatusCode.InternalServerError
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


    @Suppress("UNCHECKED_CAST")
    private fun <T : BaseDto, D> OwleApiCall<T>.responseSerializer(
        one: KSerializer<T>,
        many: KSerializer<List<T>>,
        none: KSerializer<Unit>
    ): KSerializer<ApiResponse<D>> = when (this) {
        is OwleApiCall.GetOne,
        is OwleApiCall.CreateOne,
        is OwleApiCall.UpdateOne ->
            ApiResponse.serializer(one) as KSerializer<ApiResponse<D>>

        is OwleApiCall.GetMany,
        is OwleApiCall.CreateMany,
        is OwleApiCall.UpdateMany ->
            ApiResponse.serializer(many) as KSerializer<ApiResponse<D>>

        is OwleApiCall.DeleteOne,
        is OwleApiCall.DeleteMany ->
            ApiResponse.serializer(none) as KSerializer<ApiResponse<D>>
    }

    private suspend fun <I, R> ApplicationCall.handle(
        input: AsyncResult<I>,
        owleCall: OwleApiCall<T>,
        block: suspend (I) -> AsyncResult<R>
    ) {
        val serializer = owleCall.responseSerializerForCall<R>()
        when (input) {
            is AsyncResult.Failure -> failureRespond(input, serializer)
            is AsyncResult.Success -> methodCallRespond(owleCall, serializer) { block(input.data) }
        }
    }

    private suspend fun <D> ApplicationCall.methodCallRespond(
        owleCall: OwleApiCall<*>,
        serializer: KSerializer<ApiResponse<D>>,
        resultFn: suspend () -> AsyncResult<D>
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

    private fun <D> OwleApiCall<T>.responseSerializerForCall(): KSerializer<ApiResponse<D>> {
        return responseSerializer<T, D>(dtoSerializer, dtoListSerializer, Unit.serializer())
    }

}