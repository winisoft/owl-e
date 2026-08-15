package com.wini.owle.api.workflows

import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.WorkflowFolder
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.ULongIdEndpointImpl

import com.wini.owle.validators.DtoValidator

class WorkflowFolderApiImpl(
    dll: DLL<ULong, WorkflowFolder>,
    validator: DtoValidator<WorkflowFolder>,
    override val endpointPath: String,
): ULongIdEndpointImpl<WorkflowFolder>(
    dll = dll,
    serializer = WorkflowFolder.serializer(),
    endpointPath = endpointPath,
    validator = validator
), WorkflowFolderApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-folder") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/workflow-folder/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/workflow-folder") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/workflow-folder/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/workflow-folder") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/workflow-folder/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/workflow-folder/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/workflow-folder/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}