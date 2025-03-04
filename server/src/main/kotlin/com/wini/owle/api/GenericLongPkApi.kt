package com.wini.owle.api

import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.LongArraySerializer
import kotlinx.serialization.json.Json

import kotlin.reflect.KClass


abstract class GenericLongPkApi<T : BaseDto>(
    private val dll: DLL<Long, T>,
    private val clazz: KClass<T>,
    private val serializer: KSerializer<T>
): RoutedApi {

    suspend fun getAll(call: ApplicationCall) {
        call.respond(HttpStatusCode.OK, dll.getAll())
    }

    suspend fun getById(call: ApplicationCall) {
       processIdRequest(call, dll::getById)
    }

    suspend fun create(call: ApplicationCall) {
        processTypeRequest(call, dll::create, ::deserializeSingle)
    }

    suspend fun createBatch(call: ApplicationCall) {
        processTypeRequest(call, dll::create, ::deserializeBatch)
    }

    suspend fun update(call: ApplicationCall) {
        processTypeRequest(call, dll::update, ::deserializeSingle)
    }

    suspend fun updateBatch(call: ApplicationCall) {
        processTypeRequest(call, dll::update, ::deserializeBatch)
    }

    suspend fun delete(call: ApplicationCall) {
        processIdRequest(call, dll::delete)
    }

    suspend fun deleteBatch(call: ApplicationCall) {
        processTypeRequest(call, dll::delete, ::deserializeIds)
    }

    private suspend fun <R> processTypeRequest(
        call: ApplicationCall,
        action: suspend (R) -> Any,
        deserializer: suspend (ApplicationCall) -> R?
    ) {
        runCatching { deserializer(call) }.getOrNull()
            ?.apply { call.respond(HttpStatusCode.OK, action(this)) }
            ?: call.respond(HttpStatusCode.BadRequest, "Invalid request body")
    }

    private suspend fun processIdRequest(
        call: ApplicationCall,
        action: suspend (Long) -> Any
    ) {
        call.parameters["id"]?.toLongOrNull()
            ?.apply { call.respond(HttpStatusCode.OK, action(this)) }
            ?: call.respond(HttpStatusCode.BadRequest, "Invalid ID provided")
    }

    private suspend fun deserializeSingle(call: ApplicationCall): T? =
        runCatching { call.receive(clazz) }.getOrNull()

    private suspend fun deserializeBatch(call: ApplicationCall): List<T>? =
        runCatching {
            val body = call.receiveText()
            Json.decodeFromString(ListSerializer(serializer), body)
        }.getOrNull()

    private suspend fun deserializeIds(call: ApplicationCall): List<Long>? =
        runCatching {
            val body = call.receiveText()
            Json.decodeFromString(LongArraySerializer(), body).toList()
        }.getOrNull()
}