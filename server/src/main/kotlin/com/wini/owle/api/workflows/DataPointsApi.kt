package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.DataPointDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class DataPointsApi(
    dll: DLL<Long, DataPointDto>,
    validator: (DataPointDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<DataPointDto>(
    dll = dll,
    serializer = DataPointDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/data-points") {
                call.asyncResultList(getAll())
            }
            get("/api/data-points/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/data-points") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/data-points/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/data-points") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/data-points/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/data-points/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/data-points/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}