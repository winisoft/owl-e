package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.PhaseDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class PhaseApi(
    dll: DLL<Long, PhaseDto>,
    validator: (PhaseDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<PhaseDto>(
    dll = dll,
    serializer = PhaseDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/phase") {
                call.asyncResultList(getAll())
            }
            get("/api/phase/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/phase") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/phase/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/phase") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/phase/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/phase/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/phase/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}