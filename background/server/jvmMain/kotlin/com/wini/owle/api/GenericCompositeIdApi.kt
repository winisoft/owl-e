package com.wini.owle.api

import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.mapper.CompositeIdMap
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.id.CompositeID
import kotlin.reflect.KClass


abstract class GenericCompositeIdApi<T : BaseDto>(
    private val dll: DLL<CompositeID, T>,
    private val clazz: KClass<T>,
    private val serializer: KSerializer<T>,
    private val compositeIdMapper: CompositeIdMap<T>
): RoutedApi {

    suspend fun getAll(call: ApplicationCall) {
        call.respond(HttpStatusCode.OK, dll.getAll())
    }

    suspend fun getById(call: ApplicationCall) {
        processCompositeIdRequest(call, dll::getById)
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
        processCompositeIdRequest(call, dll::delete)
    }

    suspend fun deleteBatch(call: ApplicationCall) {
        processTypeRequest(call, dll::delete) { applicationCall ->
            val dtos = deserializeBatch(applicationCall)
            dtos?.mapNotNull {
                runCatching { compositeIdMapper.buildId(it).value }
                    .onFailure { println("Failed to build CompositeID: ${it.message}") }
                    .getOrNull()
            }
        }
    }

    // Higher-order function for composite ID requests
    private suspend fun processCompositeIdRequest(
        call: ApplicationCall,
        action: suspend (CompositeID) -> Any
    ) {
        val compositeId = extractCompositeID(call)
        if (compositeId != null) {
            call.respond(HttpStatusCode.OK, action(compositeId))
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid composite ID")
        }
    }

    // Higher-order function for handling typed requests
    private suspend fun <R> processTypeRequest(
        call: ApplicationCall,
        action: suspend (R) -> Any,
        deserializer: suspend (ApplicationCall) -> R?
    ) {
        val data = deserializer(call)
        if (data != null) {
            call.respond(HttpStatusCode.OK, action(data))
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid request body")
        }
    }

    // Deserialize a single object
    private suspend fun deserializeSingle(call: ApplicationCall): T? =
        runCatching { call.receive(clazz) }.getOrNull()

    // Deserialize a batch of objects
    private suspend fun deserializeBatch(call: ApplicationCall): List<T>? =
        runCatching {
            val body = call.receiveText()
            Json.decodeFromString(ListSerializer(serializer), body)
        }.getOrNull()

    // Extract CompositeID from request
    private suspend fun extractCompositeID(call: ApplicationCall): CompositeID? {
        val dto = deserializeSingle(call)
        return dto?.let {
            runCatching { compositeIdMapper.buildId(it).value }
                .onFailure { println("Failed to build CompositeID: ${it.message}") }
                .getOrNull()
        }
    }
}
