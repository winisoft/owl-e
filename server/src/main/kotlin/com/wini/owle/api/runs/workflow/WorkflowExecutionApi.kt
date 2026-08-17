package com.wini.owle.api.runs.workflow

import com.wini.owle.dll.DLL
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator


class WorkflowExecutionApi(
    dll: DLL<ULong, WorkflowExecution>,
    validator: DtoValidator<WorkflowExecution>
): LongIdEndpointImpl<WorkflowExecution>(
    dll = dll,
    serializer = WorkflowExecution.serializer(),
    validator = validator
), IWorkflowExecutionApi  {
    
}