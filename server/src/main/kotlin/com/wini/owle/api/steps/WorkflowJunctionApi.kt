package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.steps.WorkflowJunctionDll
import com.wini.owle.dto.steps.WorkflowJunctionDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
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
                call.asyncResultMany(getAll())
            }
            get("/api/workflow-junction/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/workflow-junction") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/workflow-junction/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/workflow-junction") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/workflow-junction/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/workflow-junction/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/workflow-junction/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}