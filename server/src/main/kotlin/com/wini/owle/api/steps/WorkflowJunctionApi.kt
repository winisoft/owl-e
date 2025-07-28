package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dto.steps.WorkflowJunction
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class WorkflowJunctionApi(
    dll: DLL<Long, WorkflowJunction>,
    validator: DtoValidator<WorkflowJunction>
): LongIdEndpointImpl<WorkflowJunction>(
    dll = dll,
    serializer = WorkflowJunction.serializer(),
    validator = validator
), IWorkflowJunctionApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-junction") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/workflow-junction/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/workflow-junction") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/workflow-junction/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/workflow-junction") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/workflow-junction/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/workflow-junction/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/workflow-junction/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}