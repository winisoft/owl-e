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

    suspend fun deleteBatch(call: ApplicationCall): AsyncResult<*> {
        return Json.decodeFromString<List<Long>>(call.receiveText())
            .run(dll::deleteAll)
    }

    fun getById(call: ApplicationCall): AsyncResult<T> {
        return call.readId()
            .let { it ?: return AsyncError.ApiRequest.LongIdInvalid.asFailure() }
            .run(dll::getById)
    }

    fun delete(call: ApplicationCall): AsyncResult<*> {
        return call.readId()
            .let { it ?: return AsyncError.ApiRequest.LongIdInvalid.asFailure() }
            .run(dll::delete)
    }

    private fun ApplicationCall.readId(): Long? = parameters["id"]?.toLongOrNull()

}
