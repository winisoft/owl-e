package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Workflow
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall


class WorkflowApi(
    dll: DLL<Long, Workflow>,
    validator: DtoValidator<Workflow>
): LongIdEndpointImpl<Workflow>(
    dll = dll,
    serializer = Workflow.serializer(),
    validator = validator
), IWorkflowApi {
    override fun register(routing: Routing) {
        routing.apply {

            get("/api/workflow") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/workflow/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/workflow") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/workflow/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/workflow") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/workflow/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/workflow/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/workflow/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}