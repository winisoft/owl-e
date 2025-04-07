package com.wini.owle.api


import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.mapper.CompositeIdFactory
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.id.CompositeID


abstract class CompositeIdEndpointImpl<T: BaseDto>(
    val dll: DLL<CompositeID, T>,
    serializer: KSerializer<T>,
    val validator: (T) -> Boolean,
    open val compositeIdFactory: CompositeIdFactory<T>
): BaseEndpointImpl<CompositeID, T>(
    dll = dll,
    serializer = serializer,
    validator = validator,
    listSerializer = ListSerializer(serializer)
) {

    suspend fun getById(call: ApplicationCall): AsyncResult<T> {
        return call.readId()
            .let { it ?: return AsyncError.ApiRequest.SerializationError.asFailure() }
            .let { compositeIdFactory.idFromDto(it) }
            .run(dll::getById)
    }

    suspend fun delete(call: ApplicationCall): AsyncResult<Unit> {
        return call.readId()
            .let { it ?: return AsyncError.ApiRequest.LongIdInvalid.asFailure() }
            .let { compositeIdFactory.idFromDto(it) }
            .run(dll::delete)
    }

    suspend fun deleteBatch(call: ApplicationCall): AsyncResult<Unit> {
        return runCatching {
            Json.decodeFromString(listSerializer, call.receiveText())
        }.getOrNull()
            .let { it ?: return AsyncError.ApiRequest.LongIdInvalid.asFailure()  }
            .map { compositeIdFactory.idFromDto(it) }
            .run(dll::deleteAll)
    }

    private suspend fun ApplicationCall.readId(): T? {
        return runCatching {
            Json.decodeFromString(serializer, receiveText())
        }.getOrNull()
    }

}
