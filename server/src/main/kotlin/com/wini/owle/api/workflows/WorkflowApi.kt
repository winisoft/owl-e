package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class WorkflowApi(
    dll: DLL<Long, WorkflowDto>,
    validator: (WorkflowDto) -> Boolean = { _-> true }
): LongIdEndpointImpl<WorkflowDto>(
    dll = dll,
    serializer = WorkflowDto.serializer(),
    validator = validator
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow") {
                call.asyncResultList(getAll())
            }
            get("/api/workflow/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/workflow") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/workflow/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/workflow") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/workflow/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/workflow/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/workflow/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}