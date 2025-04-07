package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer


class WorkflowApi(
    dll: DLL<Long, WorkflowDto>
): LongIdEndpointImpl<WorkflowDto>(
    dll = dll,
    serializer = WorkflowDto.serializer(),
    validator = { _-> true }
) {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<WorkflowDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow") {
                call.asyncResult(getAll())
            }
            get("/api/workflow/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/workflow") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/workflow/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/workflow") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/workflow/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/workflow/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/workflow/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}