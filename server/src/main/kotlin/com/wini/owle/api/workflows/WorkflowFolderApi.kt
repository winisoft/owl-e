package com.wini.owle.api.workflows

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.workflows.WorkflowFolderDll
import com.wini.owle.dto.workflows.WorkflowFolderDto
import io.ktor.server.routing.*

class WorkflowFolderApi(dll: WorkflowFolderDll) : GenericLongPkApi<WorkflowFolderDto>(
    dll = dll,
    clazz = WorkflowFolderDto::class,
    serializer = WorkflowFolderDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-folder") { getAll(context.request.call) }
            get("/api/workflow-folder/{id}") { getById(context.request.call) }
            post("/api/workflow-folder") { create(context.request.call) }
            post("/api/workflow-folder/batch") { createBatch(context.request.call) }
            put("/api/workflow-folder") { update(context.request.call) }
            put("/api/workflow-folder/batch") { updateBatch(context.request.call) }
            delete("/api/workflow-folder/{id}") { super.delete(context.request.call) }
            delete("/api/workflow-folder/batch") { deleteBatch(context.request.call) }
        }
    }
}