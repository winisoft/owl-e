package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.WorkflowFolderDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall



class WorkflowFolderApi(
    dll: DLL<Long, WorkflowFolderDto>,
    validator: (WorkflowFolderDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<WorkflowFolderDto>(
    dll = dll,
    serializer = WorkflowFolderDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-folder") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/workflow-folder/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/workflow-folder") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/workflow-folder/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/workflow-folder") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/workflow-folder/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/workflow-folder/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/workflow-folder/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}