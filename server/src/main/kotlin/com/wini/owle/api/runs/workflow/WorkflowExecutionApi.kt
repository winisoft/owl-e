package com.wini.owle.api.runs.workflow

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class WorkflowExecutionApi(
    dll: DLL<Long, WorkflowExecution>,
    validator: DtoValidator<WorkflowExecution>
): LongIdEndpointImpl<WorkflowExecution>(
    dll = dll,
    serializer = WorkflowExecution.serializer(),
    validator = validator
), IWorkflowExecutionApi  {
    
}