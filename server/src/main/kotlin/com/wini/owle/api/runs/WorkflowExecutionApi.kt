package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.runs.WorkflowExecutionDll
import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class WorkflowExecutionApi(
    dll: WorkflowExecutionDll
) : LongIdEndpointImpl<WorkflowExecutionDto>(
    dll = dll,
    serializer = WorkflowExecutionDto.serializer()
)  {



    //"") { getAll(context.request.call) }
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-execution") {
                call.asyncResultMany(getAll())
            }
            get("/api/workflow-execution/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/workflow-execution") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/workflow-execution/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/workflow-execution") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/workflow-execution/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/workflow-execution/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/workflow-execution/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}