package com.wini.owle.api

import com.wini.owle.validators.DtoValidator
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.ULongDto
import com.wini.owle.error.BatchOpOfNoDataObjects
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


/**
 * Implementation of a Ktor API endpoint suited to types with ULong PK  (BIGINT UNSIGNED). The must be [ULongDto].
 */
open class ULongIdEndpointImpl<T: BaseDto>(
    dll: DLL<ULong, T>,
    validator: DtoValidator<T> = DtoValidator.alwaysValid(),
    override val endpointPath: String,
    serializer: KSerializer<T>,
): BaseApiImpl<ULong, T>(
    dll = dll,
    validator = validator,
    serializer = serializer
) {

    override fun ApplicationCall.readId(): AsyncResult<ULong> = parameters["id"]
        .ifNullExit {
            return AsyncResult.Failure(error = MissingParameter("id"))
        }.toULongOrNull().ifNullExit {
            return AsyncResult.Failure(error = ParamValueMalformedLong("id"))
        }.let {
            AsyncResult.Success(it)
        }

    override suspend fun ApplicationCall.readIds(): AsyncResult<List<ULong>> {
        runCatching {
            Json.decodeFromString(ListSerializer(ULong.serializer()), receiveText())
        }.getOrElse {
            return RequestBodyMalformed.asFailure()
        }.takeIf { it.isNotEmpty() }.ifNullExit {
            return EmptyListNotAllowed.asFailure()
        }.let {
            return AsyncResult.Success(it)
        }
    }

}
