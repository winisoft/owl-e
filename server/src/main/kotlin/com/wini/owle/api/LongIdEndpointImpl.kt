package com.wini.owle.api

import com.wini.owle.validators.DtoValidator
import com.wini.owle.async.AsyncError.ApiRequestError
import com.wini.owle.async.AsyncError.ApiRequestError.MissingParameter
import com.wini.owle.async.AsyncError.ApiRequestError.ParamValueMalformedLong
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.ifNullExit
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json


abstract class LongIdEndpointImpl<T: BaseDto>(
    override val dll: DLL<Long, T>,
    serializer: KSerializer<T>,
    override val validator: DtoValidator<T> = DtoValidator.alwaysValid(),
): BaseEndpointImpl<Long, T>(
    dll = dll,
    validator = validator,
    dtoSerializer = serializer,
    dtoListSerializer = ListSerializer(serializer)
) {

//    suspend fun ApplicationCall.execute(owleCall: OwleApiCall) {
//        when (owleCall) {
//            is OwleApiCall.GetMany ->
//                methodCallRespond(owleCall, responseManySerializer) { dll.getAll() }
//            is OwleApiCall.GetOne ->
//                when (val idResult = readId()) {
//                    is AsyncResult.Failure -> failureRespond(idResult, responseOneSerializer)
//                    is AsyncResult.Success -> methodCallRespond(owleCall, responseOneSerializer) {
//                        dll.getById(idResult.data)
//                    }
//                }
//            is OwleApiCall.DeleteOne -> when (val idResult = readId()) {
//                is AsyncResult.Failure -> failureRespond(idResult, responseNoneSerializer)
//                is AsyncResult.Success -> methodCallRespond(owleCall, responseNoneSerializer) {
//                    dll.delete(idResult.data)
//                }
//            }
//            is OwleApiCall.CreateOne -> when (val dtoResult: AsyncResult<T> = readDto(dtoSerializer, validator)) {
//                is AsyncResult.Failure -> failureRespond(dtoResult, responseOneSerializer)
//                is AsyncResult.Success<T> -> methodCallRespond(owleCall, responseOneSerializer) {
//                    dll.create(dtoResult.data)
//                }
//            }
//            is OwleApiCall.UpdateOne -> when (val dtoResult = readDto(dtoSerializer, validator)) {
//                is AsyncResult.Failure -> failureRespond(dtoResult, responseOneSerializer)
//                is AsyncResult.Success -> methodCallRespond(owleCall, responseOneSerializer) {
//                    dll.update(dtoResult.data)
//                }
//            }
//            is OwleApiCall.DeleteMany -> when (val idsResult = readIds()) {
//                is AsyncResult.Failure -> failureRespond(idsResult, responseNoneSerializer)
//                is AsyncResult.Success -> methodCallRespond(owleCall, responseNoneSerializer) {
//                    dll.deleteAll(idsResult.data)
//                }
//            }
//            is OwleApiCall.CreateMany -> when (val dtosResult = readDtos(dtoListSerializer, validator)) {
//                is AsyncResult.Failure -> failureRespond(dtosResult, responseManySerializer)
//                is AsyncResult.Success -> methodCallRespond(owleCall, responseManySerializer) {
//                    dll.createAll(dtosResult.data)
//                }
//            }
//            is OwleApiCall.UpdateMany -> when (val dtosResult = readDtos(dtoListSerializer, validator)) {
//                is AsyncResult.Failure -> failureRespond(dtosResult, responseManySerializer)
//                is AsyncResult.Success -> methodCallRespond(owleCall, responseManySerializer) {
//                    dll.createAll(dtosResult.data)
//                }
//            }
//        }
//    }

    override fun ApplicationCall.readId(): AsyncResult<Long> = parameters["id"]
        .ifNullExit {
            return AsyncResult.Failure(error = MissingParameter("id"))
        }.toLongOrNull().ifNullExit {
            return AsyncResult.Failure(error = ParamValueMalformedLong("id"))
        }.let {
            AsyncResult.Success(it)
        }

    override suspend fun ApplicationCall.readIds(): AsyncResult<List<Long>> {
        return Json.decodeFromString<List<Long>>(receiveText()).ifNullExit {
            return ApiRequestError.RequestBodyMalformed.asFailure()
        }.takeIf { it.isNotEmpty() }.ifNullExit {
            return ApiRequestError.EmptyListNotAllowed.asFailure()
        }.let {
            AsyncResult.Success(it)
        }
    }
}
