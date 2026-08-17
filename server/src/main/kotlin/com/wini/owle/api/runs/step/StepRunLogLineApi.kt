package com.wini.owle.api.runs.step

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.step.StepRunLogLine
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class StepRunLogLineApi(
    dll: DLL<Long, StepRunLogLine>,
    validator: DtoValidator<StepRunLogLine>
): LongIdEndpointImpl<StepRunLogLine>(
    dll = dll,
    serializer = StepRunLogLine.serializer(),
    validator = validator
), IStepRunLogLineApi  {

}