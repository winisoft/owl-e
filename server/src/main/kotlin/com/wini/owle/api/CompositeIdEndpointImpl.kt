package com.wini.owle.api


import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.mapper.CompositeIdFactory
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.serialization.KSerializer
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
    validator = validator
) {



    suspend fun deleteBatch(call: ApplicationCall): AsyncResult<Unit> {
        return Json.decodeFromString<List<T>>(call.receiveText())
            .map { compositeIdFactory.idFromDto(it) }
            .run(dll::deleteAll)
    }

}
