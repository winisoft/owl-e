package com.wini.owle.api.runs.workflow

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class WorkflowTestResultApi(
    dll: DLL<Long, WorkflowTestResult>,
    validator: DtoValidator<WorkflowTestResult>
): LongIdEndpointImpl<WorkflowTestResult>(
    dll = dll,
    serializer = WorkflowTestResult.serializer(),
    validator = validator
), IWorkflowTestResultApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-result") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/test-result/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/test-result") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/test-result/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/test-result") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/test-result/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/test-result/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) }
            }
            delete("/api/test-result/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}