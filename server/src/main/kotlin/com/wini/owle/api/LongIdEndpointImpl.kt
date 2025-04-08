package com.wini.owle.api

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json


abstract class LongIdEndpointImpl<T: BaseDto>(
    val dll: DLL<Long, T>,
    serializer: KSerializer<T>,
    val validator: (T) -> Boolean = { _-> true },
): BaseEndpointImpl<Long, T>(
    dll = dll,
    validator = validator,
    dtoSerializer = serializer,
    dtoListSerializer = ListSerializer(serializer)
) {

    fun ApplicationCall.getById(): AsyncResult<T> {
        return readId()
            .let { it ?: return AsyncError.ApiRequest.InvalidLongId.asFailure() }
            .run(dll::getById)
    }

    fun ApplicationCall.delete(): AsyncResult<*> {
        return readId()
            .let { it ?: return AsyncError.ApiRequest.InvalidLongId.asFailure() }
            .run(dll::delete)
    }

    suspend fun ApplicationCall.deleteBatch(): AsyncResult<*> {
        return Json.decodeFromString<List<Long>>(receiveText())
            .run(dll::deleteAll)
    }

    private fun ApplicationCall.readId(): Long? = parameters["id"]?.toLongOrNull()

}
