package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dll.workflows.PhaseDll
import com.wini.owle.dto.workflows.PhaseDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer

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
                call.asyncResultMany(getAll())
            }
            get("/api/phase/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/phase") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/phase/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/phase") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/phase/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/phase/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/phase/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}