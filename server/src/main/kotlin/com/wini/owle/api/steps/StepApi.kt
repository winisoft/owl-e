package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Step
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class StepApi(
    dll: DLL<Long, Step>,
    validator: DtoValidator<Step>
): LongIdEndpointImpl<Step>(
    dll = dll,
    serializer = Step.serializer(),
    validator = validator
) {

}