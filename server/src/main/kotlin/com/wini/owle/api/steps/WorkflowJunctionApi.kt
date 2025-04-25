package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dto.steps.WorkflowJunction
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.validators.DtoValidator


class WorkflowJunctionApi(
    dll: DLL<Long, WorkflowJunction>,
    validator: DtoValidator<WorkflowJunction>
): LongIdEndpointImpl<WorkflowJunction>(
    dll = dll,
    serializer = WorkflowJunction.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-junction") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/workflow-junction/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/workflow-junction") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/workflow-junction/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/workflow-junction") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/workflow-junction/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/workflow-junction/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/workflow-junction/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}