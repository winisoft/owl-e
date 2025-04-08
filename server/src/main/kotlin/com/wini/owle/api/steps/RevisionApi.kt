package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.RevisionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class RevisionApi(
    dll: DLL<Long, RevisionDto>,
    validator: (RevisionDto) -> Boolean = { _-> true }
): LongIdEndpointImpl<RevisionDto>(
    dll = dll,
    serializer = RevisionDto.serializer(),
    validator = validator
) {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/revision") {
                call.asyncResultList(getAll())
            }
            get("/api/revision/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/revision") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/revision/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/revision") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/revision/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/revision/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/revision/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}