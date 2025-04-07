package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.steps.RevisionDll
import com.wini.owle.dto.steps.RevisionDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class RevisionApi(
    dll: RevisionDll
): LongIdEndpointImpl<RevisionDto>(
    dll = dll,
    serializer = RevisionDto.serializer(),
    validator = { _-> true }
) {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<RevisionDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/revision") {
                call.asyncResult(getAll())
            }
            get("/api/revision/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/revision") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/revision/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/revision") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/revision/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/revision/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/revision/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}