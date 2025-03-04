package com.wini.owle.api.workflows

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dto.*
import com.wini.owle.dll.workflows.*
import io.ktor.server.routing.*


class WorkflowApi(dll: WorkflowDll) : GenericLongPkApi<WorkflowDto>(
    dll = dll,
    clazz = WorkflowDto::class,
    serializer = WorkflowDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow") { getAll(context.request.call) }
            get("/api/workflow/{id}") { getById(context.request.call) }
            post("/api/workflow") { create(context.request.call) }
            post("/api/workflow/batch") { createBatch(context.request.call) }
            put("/api/workflow") { update(context.request.call) }
            put("/api/workflow/batch") { updateBatch(context.request.call) }
            delete("/api/workflow/{id}") { super.delete(context.request.call) }
            delete("/api/workflow/batch") { deleteBatch(context.request.call) }
        }
    }
}