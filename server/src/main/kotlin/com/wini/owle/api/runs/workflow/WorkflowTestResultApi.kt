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

}