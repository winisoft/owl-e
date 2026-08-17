package com.wini.owle.api.workflows

import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Workflow
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.ULongIdEndpointImpl


class WorkflowApiImpl(
    dll: DLL<ULong, Workflow>,
    validator: DtoValidator<Workflow>,
    override val endpointPath: String,
): ULongIdEndpointImpl<Workflow>(
    dll = dll,
    validator = validator,
    endpointPath = endpointPath,
    serializer = Workflow.serializer()
) {
}