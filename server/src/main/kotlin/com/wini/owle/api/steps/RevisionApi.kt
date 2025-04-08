package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.RevisionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class RevisionApi(
    dll: DLL<Long, RevisionDto>
): LongIdEndpointImpl<RevisionDto>(
    dll = dll,
    serializer = RevisionDto.serializer(),
    validator = { _-> true }
) {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/revision") {
                call.asyncResultList(getAll())
            }
            get("/api/revision/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/revision") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/revision/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/revision") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/revision/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/revision/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/revision/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}