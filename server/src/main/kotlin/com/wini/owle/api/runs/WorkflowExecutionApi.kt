package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.WorkflowExecutionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator


class WorkflowExecutionApi(
    dll: DLL<Long, WorkflowExecutionDto>,
    validator: DtoValidator<WorkflowExecutionDto>
): LongIdEndpointImpl<WorkflowExecutionDto>(
    dll = dll,
    serializer = WorkflowExecutionDto.serializer(),
    validator = validator
)  {
    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-execution") {
                call.execute(OwleApiCall.GetMany(WorkflowExecutionDto::class))
            }
            get("/api/workflow-execution/{id}") {
                call.execute(OwleApiCall.GetOne(WorkflowExecutionDto::class))
            }
            post("/api/workflow-execution") {
                call.execute(OwleApiCall.CreateOne(WorkflowExecutionDto::class))
            }
            post("/api/workflow-execution/batch") {
                call.execute(OwleApiCall.CreateMany(WorkflowExecutionDto::class))
            }
            put("/api/workflow-execution") {
                call.execute(OwleApiCall.UpdateOne(WorkflowExecutionDto::class))
            }
            put("/api/workflow-execution/batch") {
                call.execute(OwleApiCall.UpdateMany(WorkflowExecutionDto::class))
            }
            delete("/api/workflow-execution/{id}") {
                call.execute(OwleApiCall.DeleteOne(WorkflowExecutionDto::class))
            }
            delete("/api/workflow-execution/batch") {
                call.execute(OwleApiCall.DeleteMany(WorkflowExecutionDto::class))
            }
        }
    }
}