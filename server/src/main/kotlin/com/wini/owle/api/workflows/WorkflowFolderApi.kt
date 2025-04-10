package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.WorkflowFolderDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class WorkflowFolderApi(
    dll: DLL<Long, WorkflowFolderDto>,
    validator: DtoValidator<WorkflowFolderDto>
): LongIdEndpointImpl<WorkflowFolderDto>(
    dll = dll,
    serializer = WorkflowFolderDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/workflow-folder") {
                call.execute(OwleApiCall.GetMany(WorkflowFolderDto::class))
            }
            get("/api/workflow-folder/{id}") {
                call.execute(OwleApiCall.GetOne(WorkflowFolderDto::class))
            }
            post("/api/workflow-folder") {
                call.execute(OwleApiCall.CreateOne(WorkflowFolderDto::class))
            }
            post("/api/workflow-folder/batch") {
                call.execute(OwleApiCall.CreateMany(WorkflowFolderDto::class))
            }
            put("/api/workflow-folder") {
                call.execute(OwleApiCall.UpdateOne(WorkflowFolderDto::class))
            }
            put("/api/workflow-folder/batch") {
                call.execute(OwleApiCall.UpdateMany(WorkflowFolderDto::class))
            }
            delete("/api/workflow-folder/{id}") {
                call.execute(OwleApiCall.DeleteOne(WorkflowFolderDto::class)) 
            }
            delete("/api/workflow-folder/batch") {
                call.execute(OwleApiCall.DeleteMany(WorkflowFolderDto::class))
            }
        }
    }
}