package com.wini.owle.api.runs.workflow

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator


class WorkflowExecutionApi(
    dll: DLL<Long, WorkflowExecution>,
    validator: DtoValidator<WorkflowExecution>
): LongIdEndpointImpl<WorkflowExecution>(
    dll = dll,
    serializer = WorkflowExecution.serializer(),
    validator = validator
)  {
    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-execution") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/workflow-execution/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/workflow-execution") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/workflow-execution/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/workflow-execution") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/workflow-execution/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/workflow-execution/{id}") {
                call.execute(OwleApiCall.DeleteOne)
            }
            delete("/api/workflow-execution/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}