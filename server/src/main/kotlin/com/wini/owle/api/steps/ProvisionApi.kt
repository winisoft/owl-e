package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ProvisionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class ProvisionApi(
    dll: DLL<Long, ProvisionDto>,
    validator: (ProvisionDto) -> Boolean = { _-> true }
): LongIdEndpointImpl<ProvisionDto>(
    dll = dll,
    serializer = ProvisionDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/provision") {
                call.asyncResultList(getAll())
            }
            get("/api/provision/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/provision") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/provision/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/provision") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/provision/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/provision/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/provision/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}