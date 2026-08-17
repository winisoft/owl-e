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
) {

}