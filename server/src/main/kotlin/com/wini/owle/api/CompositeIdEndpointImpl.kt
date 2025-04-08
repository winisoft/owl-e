package com.wini.owle.api


import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.entity.composteid.CompositeIdFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.util.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.id.CompositeID


abstract class CompositeIdEndpointImpl<T: BaseDto>(
    val dll: DLL<CompositeID, T>,
    serializer: KSerializer<T>,
    val validator: (T) -> Boolean,
    open val compositeIdFactory: CompositeIdFactory<T>
): BaseEndpointImpl<CompositeID, T>(
    dll = dll,
    dtoSerializer = serializer,
    validator = validator,
    dtoListSerializer = ListSerializer(serializer)
) {

    fun ApplicationCall.getById(): AsyncResult<T> {
        return compositeIdFactory.fromRequestParams(request.queryParameters.toMap())
            .let { it ?: return AsyncError.ApiRequest.CompositeIdError.asFailure() }
            .run(dll::getById)
    }

    fun ApplicationCall.delete(): AsyncResult<*> {
        return compositeIdFactory.fromRequestParams(request.queryParameters.toMap())
            .let { it ?: return AsyncError.ApiRequest.CompositeIdError.asFailure() }
            .run(dll::delete)
    }

    suspend fun ApplicationCall.deleteBatch(): AsyncResult<*> {
        return runCatching {
            Json.decodeFromString(dtoListSerializer, receiveText())
        }.getOrNull()
            ?.map { compositeIdFactory.fromDtoInstance(it) }
            .let { it ?: return AsyncError.ApiRequest.LongIdInvalid.asFailure()  }
            .filterNotNull()
            .run(dll::deleteAll)
    }

    abstract fun ApplicationCall.readId(): CompositeID?

}
