package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.workflows.PhaseDll
import com.wini.owle.dto.workflows.PhaseDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

class PhaseApi(
    dll: PhaseDll
) : LongIdEndpointImpl<PhaseDto>(
    dll = dll,
    serializer = PhaseDto.serializer(),
    validator = { _-> true }
) {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<PhaseDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/phase") {
                call.asyncResult(getAll())
            }
            get("/api/phase/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/phase") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/phase/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/phase") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/phase/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
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