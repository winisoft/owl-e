package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.WorkflowFolderDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class WorkflowFolderApi(
    dll: DLL<Long, WorkflowFolderDto>,
    validator: (WorkflowFolderDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<WorkflowFolderDto>(
    dll = dll,
    serializer = WorkflowFolderDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-folder") {
                call.asyncResultList(getAll())
            }
            get("/api/workflow-folder/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/workflow-folder") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/workflow-folder/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/workflow-folder") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/workflow-folder/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/workflow-folder/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/workflow-folder/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}