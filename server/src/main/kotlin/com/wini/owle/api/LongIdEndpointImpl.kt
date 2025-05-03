package com.wini.owle.api

import com.wini.owle.validators.DtoValidator
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.error.EmptyListNotAllowed
import com.wini.owle.error.MissingParameter
import com.wini.owle.error.ParamValueMalformedLong
import com.wini.owle.error.RequestBodyMalformed
import com.wini.owle.ifNullExit
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
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

    override fun ApplicationCall.readId(): AsyncResult<Long> = parameters["id"]
        .ifNullExit {
            return AsyncResult.Failure(error = MissingParameter("id"))
        }.toLongOrNull().ifNullExit {
            return AsyncResult.Failure(error = ParamValueMalformedLong("id"))
        }.let {
            AsyncResult.Success(it)
        }

    override suspend fun ApplicationCall.readIds(): AsyncResult<List<Long>> {
        runCatching {
            Json.decodeFromString(ListSerializer(Long.serializer()), receiveText())
        }.getOrElse {
            return RequestBodyMalformed.asFailure()
        }.takeIf { it.isNotEmpty() }.ifNullExit {
            return EmptyListNotAllowed.asFailure()
        }.let {
            return AsyncResult.Success(it)
        }
    }
}
