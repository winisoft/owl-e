package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.WorkflowExecutionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class WorkflowExecutionApi(
    dll: DLL<Long, WorkflowExecutionDto>
) : LongIdEndpointImpl<WorkflowExecutionDto>(
    dll = dll,
    serializer = WorkflowExecutionDto.serializer()
)  {

    //"") { getAll(context.request.call) }
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-execution") {
                call.asyncResultList(getAll())
            }
            get("/api/workflow-execution/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/workflow-execution") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/workflow-execution/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/workflow-execution") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/workflow-execution/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/workflow-execution/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/workflow-execution/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}