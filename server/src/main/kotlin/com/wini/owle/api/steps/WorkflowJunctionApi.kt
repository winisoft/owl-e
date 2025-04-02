package com.wini.owle.api.steps

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.steps.WorkflowJunctionDll
import com.wini.owle.dto.steps.WorkflowJunctionDto
import io.ktor.server.routing.*

class WorkflowJunctionApi(dll: WorkflowJunctionDll) : GenericLongPkApi<WorkflowJunctionDto>(
    dll = dll,
    clazz = WorkflowJunctionDto::class,
    serializer = WorkflowJunctionDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-junction") { getAll(context.request.call) }
            get("/api/workflow-junction/{id}") { getById(context.request.call) }
            post("/api/workflow-junction") { create(context.request.call) }
            post("/api/workflow-junction/batch") { createBatch(context.request.call) }
            put("/api/workflow-junction") { update(context.request.call) }
            put("/api/workflow-junction/batch") { updateBatch(context.request.call) }
            delete("/api/workflow-junction/{id}") { super.delete(context.request.call) }
            delete("/api/workflow-junction/batch") { deleteBatch(context.request.call) }
        }
    }
}