package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class WorkflowApi(
    dll: DLL<Long, WorkflowDto>
): LongIdEndpointImpl<WorkflowDto>(
    dll = dll,
    serializer = WorkflowDto.serializer(),
    validator = { _-> true }
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow") {
                call.asyncResultList(getAll())
            }
            get("/api/workflow/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/workflow") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/workflow/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/workflow") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/workflow/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/workflow/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/workflow/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}