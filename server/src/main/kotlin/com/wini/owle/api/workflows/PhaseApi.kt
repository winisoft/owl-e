package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.PhaseDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class PhaseApi(
    dll: DLL<Long, PhaseDto>
) : LongIdEndpointImpl<PhaseDto>(
    dll = dll,
    serializer = PhaseDto.serializer(),
    validator = { _-> true }
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/phase") {
                call.asyncResultList(getAll())
            }
            get("/api/phase/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/phase") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/phase/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/phase") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/phase/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/phase/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/phase/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}