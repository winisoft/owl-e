package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator


class WorkflowApi(
    dll: DLL<Long, WorkflowDto>,
    validator: DtoValidator<WorkflowDto>
): LongIdEndpointImpl<WorkflowDto>(
    dll = dll,
    serializer = WorkflowDto.serializer(),
    validator = validator
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/workflow/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/workflow") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/workflow/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/workflow") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/workflow/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/workflow/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/workflow/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}