package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dto.steps.WorkflowJunctionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.validators.DtoValidator


class WorkflowJunctionApi(
    dll: DLL<Long, WorkflowJunctionDto>,
    validator: DtoValidator<WorkflowJunctionDto>
): LongIdEndpointImpl<WorkflowJunctionDto>(
    dll = dll,
    serializer = WorkflowJunctionDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-junction") {
                call.execute(OwleApiCall.GetMany(WorkflowJunctionDto::class))
            }
            get("/api/workflow-junction/{id}") {
                call.execute(OwleApiCall.GetOne(WorkflowJunctionDto::class))
            }
            post("/api/workflow-junction") {
                call.execute(OwleApiCall.CreateOne(WorkflowJunctionDto::class))
            }
            post("/api/workflow-junction/batch") {
                call.execute(OwleApiCall.CreateMany(WorkflowJunctionDto::class))
            }
            put("/api/workflow-junction") {
                call.execute(OwleApiCall.UpdateOne(WorkflowJunctionDto::class))
            }
            put("/api/workflow-junction/batch") {
                call.execute(OwleApiCall.UpdateMany(WorkflowJunctionDto::class))
            }
            delete("/api/workflow-junction/{id}") {
                call.execute(OwleApiCall.DeleteOne(WorkflowJunctionDto::class)) 
            }
            delete("/api/workflow-junction/batch") {
                call.execute(OwleApiCall.DeleteMany(WorkflowJunctionDto::class))
            }
        }
    }
}