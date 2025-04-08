package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ProvisionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class ProvisionApi(
    dll: DLL<Long, ProvisionDto>
): LongIdEndpointImpl<ProvisionDto>(
    dll = dll,
    serializer = ProvisionDto.serializer()
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/provision") {
                call.asyncResultList(getAll())
            }
            get("/api/provision/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/provision") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/provision/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/provision") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/provision/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/provision/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/provision/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}