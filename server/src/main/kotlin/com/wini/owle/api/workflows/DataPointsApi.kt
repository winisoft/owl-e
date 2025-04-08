package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.DataPointDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class DataPointsApi(
    dll: DLL<Long, DataPointDto>
) : LongIdEndpointImpl<DataPointDto>(
    dll = dll,
    serializer = DataPointDto.serializer(),
    validator = { _-> true }
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/data-points") {
                call.asyncResultList(getAll())
            }
            get("/api/data-points/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/data-points") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/data-points/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/data-points") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/data-points/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/data-points/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/data-points/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}