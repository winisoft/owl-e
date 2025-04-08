package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.steps.WorkflowJunctionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class WorkflowJunctionApi(
    dll: DLL<Long, WorkflowJunctionDto>
) : LongIdEndpointImpl<WorkflowJunctionDto>(
    dll = dll,
    serializer = WorkflowJunctionDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-junction") {
                call.asyncResultList(getAll())
            }
            get("/api/workflow-junction/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/workflow-junction") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/workflow-junction/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/workflow-junction") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/workflow-junction/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/workflow-junction/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/workflow-junction/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}