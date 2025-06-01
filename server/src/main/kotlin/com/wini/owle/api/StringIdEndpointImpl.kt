package com.wini.owle.api

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.StringDto
import com.wini.owle.error.BatchOpOfNoDataObjects
import com.wini.owle.error.MissingParameter
import com.wini.owle.error.ParamValueMalformedLong
import com.wini.owle.error.RequestBodyMalformed
import com.wini.owle.ifNullExit
import com.wini.owle.validators.DtoValidator
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json


/**
 * Implementation of a Ktor API endpoint suited to types with primary key on a single string. The must be [StringDto].
 */
open class StringIdEndpointImpl<T: StringDto>(
    val dll: DLL<String, T>,
    val validator: DtoValidator<T> = DtoValidator.alwaysValid(),
    override val endpointPath: String,
    serializer: KSerializer<T>,
): BaseApiImpl<String, T>(
    dll = dll,
    validator = validator,
    serializer = serializer
) {

    override fun ApplicationCall.readId(): AsyncResult<String> = parameters["id"]
        .ifNullExit {
            return AsyncResult.Failure(error = MissingParameter("id"))
        }.ifNullExit {
            return AsyncResult.Failure(error = ParamValueMalformedLong("id"))
        }.let {
            AsyncResult.Success(it)
        }

    override suspend fun ApplicationCall.readIds(): AsyncResult<List<String>> {
        runCatching {
            Json.decodeFromString(ListSerializer(String.serializer()), receiveText())
        }.getOrElse {
            return RequestBodyMalformed.asFailure()
        }.takeIf { it.isNotEmpty() }.ifNullExit {
            return BatchOpOfNoDataObjects.asFailure()
        }.let {
            return AsyncResult.Success(it)
        }
    }
}
