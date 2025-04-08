package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.WorkflowExecutionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class WorkflowExecutionApi(
    dll: DLL<Long, WorkflowExecutionDto>,
    validator: (WorkflowExecutionDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<WorkflowExecutionDto>(
    dll = dll,
    serializer = WorkflowExecutionDto.serializer(),
    validator = validator
)  {

    //"") { getAll(context.request.call) }
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-execution") {
                call.asyncResultList(getAll())
            }
            get("/api/workflow-execution/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/workflow-execution") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/workflow-execution/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/workflow-execution") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/workflow-execution/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/workflow-execution/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/workflow-execution/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}