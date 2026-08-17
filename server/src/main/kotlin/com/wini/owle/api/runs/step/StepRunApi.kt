package com.wini.owle.api.runs.step

import com.wini.owle.api.OwleApiCall
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dto.runs.step.StepTestRun
import com.wini.owle.schema.Schema
import io.ktor.server.application.call

class StepRunApi(
    dll: DLL<ULong, StepTestRun>,

    validator: DtoValidator<Schema.StepRun>
): ULongIdEndpointImpl<Schema.StepRun>(
    dll = dll,
    serializer = StepRun.serializer(),
    validator = validator
), StepRunApi  {

}