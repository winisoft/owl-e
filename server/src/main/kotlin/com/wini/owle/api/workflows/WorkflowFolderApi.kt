package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.workflows.WorkflowFolderDll
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.dto.workflows.WorkflowFolderDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class WorkflowFolderApi(
    dll: WorkflowFolderDll
) : LongIdEndpointImpl<WorkflowFolderDto>(
    dll = dll,
    serializer = WorkflowFolderDto.serializer(),
    validator = { _-> true }
) {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<WorkflowFolderDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-folder") {
                call.asyncResult(getAll())
            }
            get("/api/workflow-folder/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/workflow-folder") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/workflow-folder/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/workflow-folder") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/workflow-folder/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/workflow-folder/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/workflow-folder/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}