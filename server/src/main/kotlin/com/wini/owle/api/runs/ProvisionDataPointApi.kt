package com.wini.owle.api.runs

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.steps.ProvisionDataPointDll
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.mapper.CompositeIdFactory
import com.wini.owle.mapper.ProvisionDataPointIdFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json


class ProvisionDataPointApi(
    dll: ProvisionDataPointDll
) : CompositeIdEndpointImpl<ProvisionDataPointDto>(
    dll = dll,
    serializer = ProvisionDataPointDto.serializer(),
    compositeIdFactory = ProvisionDataPointIdFactory,
    validator = { _-> true }
) {

    suspend fun getById(call: ApplicationCall): AsyncResult<ProvisionDataPointDto> {
        return Json.decodeFromString<ProvisionDataPointDto>(call.receiveText())
            .run { compositeIdFactory.idFromDto(this) }
            .run(dll::getById)
    }

    suspend fun delete(call: ApplicationCall): AsyncResult<Unit> {
        return Json.decodeFromString<ProvisionDataPointDto>(call.receiveText())
            .run { compositeIdFactory.idFromDto(this) }
            .run(dll::delete)
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/provision-datapoint") {
                call.asyncResult(getAll())
            }
            get("/api/provision-datapoint/{id}") {
                getById(call)
            }
            post("/api/provision-datapoint") {
                create(call, validator)
            }
            post("/api/provision-datapoint/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/provision-datapoint") {
                update(call, validator)
            }
            put("/api/provision-datapoint/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/provision-datapoint/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/provision-datapoint/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}