package com.wini.owle.api

import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.StringDto
import com.wini.owle.error.EmptyListNotAllowed
import com.wini.owle.error.MissingParameter
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

    /**
     * A string id is whatever the caller sent, so the only failure is its absence.
     *
     * This was copied from the ULong endpoint and kept that version's second check, which sat
     * after the null test and so could never fire - and reported `ParamValueMalformedLong`, a
     * number-format error, for an id that is not a number. A blank id is treated as absent:
     * `?id=` addresses no row.
     */
    override fun ApplicationCall.readId(): AsyncResult<String> = parameters["id"]
        ?.takeIf { it.isNotBlank() }
        .ifNullExit {
            return AsyncResult.Failure(error = MissingParameter("id"))
        }.let {
            AsyncResult.Success(it)
        }

    override suspend fun ApplicationCall.readIds(): AsyncResult<List<String>> {
        runCatching {
            Json.decodeFromString(ListSerializer(String.serializer()), receiveText())
        }.getOrElse {
            return RequestBodyMalformed.asFailure()
        }.takeIf { it.isNotEmpty() }.ifNullExit {
            return EmptyListNotAllowed.asFailure()
        }.let {
            return AsyncResult.Success(it)
        }
    }
}
