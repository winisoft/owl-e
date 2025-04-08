package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.steps.WorkflowJunctionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class WorkflowJunctionApi(
    dll: DLL<Long, WorkflowJunctionDto>,
    validator: (WorkflowJunctionDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<WorkflowJunctionDto>(
    dll = dll,
    serializer = WorkflowJunctionDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-junction") {
                call.asyncResultList(getAll())
            }
            get("/api/workflow-junction/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/workflow-junction") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/workflow-junction/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/workflow-junction") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/workflow-junction/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/workflow-junction/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/workflow-junction/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}