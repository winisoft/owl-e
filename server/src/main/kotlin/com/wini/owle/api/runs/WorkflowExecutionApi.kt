package com.wini.owle.api.runs

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.runs.WorkflowExecutionDll
import com.wini.owle.dto.WorkflowExecutionDto
import io.ktor.server.routing.*

class WorkflowExecutionApi(dll: WorkflowExecutionDll) : GenericLongPkApi<WorkflowExecutionDto>(
    dll = dll,
    clazz = WorkflowExecutionDto::class,
    serializer = WorkflowExecutionDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-execution") { getAll(context.request.call) }
            get("/api/workflow-execution/{id}") { getById(context.request.call) }
            post("/api/workflow-execution") { create(context.request.call) }
            post("/api/workflow-execution/batch") { createBatch(context.request.call) }
            put("/api/workflow-execution") { update(context.request.call) }
            put("/api/workflow-execution/batch") { updateBatch(context.request.call) }
            delete("/api/workflow-execution/{id}") { super.delete(context.request.call) }
            delete("/api/workflow-execution/batch") { deleteBatch(context.request.call) }
        }
    }
}