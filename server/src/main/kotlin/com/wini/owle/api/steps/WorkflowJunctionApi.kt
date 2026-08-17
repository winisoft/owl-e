package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dto.steps.WorkflowJunction
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class WorkflowJunctionApi(
    dll: DLL<Long, WorkflowJunction>,
    validator: DtoValidator<WorkflowJunction>
): LongIdEndpointImpl<WorkflowJunction>(
    dll = dll,
    serializer = WorkflowJunction.serializer(),
    validator = validator
), IWorkflowJunctionApi  {

}