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
import kotlinx.serialization.builtins.ListSerializer

class WorkflowFolderApi(
    dll: WorkflowFolderDll
) : LongIdEndpointImpl<WorkflowFolderDto>(
    dll = dll,
    serializer = WorkflowFolderDto.serializer(),
    validator = { _-> true }
) {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-folder") {
                call.asyncResultMany(getAll())
            }
            get("/api/workflow-folder/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/workflow-folder") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/workflow-folder/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/workflow-folder") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/workflow-folder/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
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