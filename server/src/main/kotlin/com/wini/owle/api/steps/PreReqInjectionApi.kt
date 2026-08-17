package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.InitialDataPointInjector
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class PreReqInjectionApi(
    dll: DLL<Long, InitialDataPointInjector>,
    validator: DtoValidator<InitialDataPointInjector>
): LongIdEndpointImpl<InitialDataPointInjector>(
    dll = dll,
    serializer = InitialDataPointInjector.serializer(),
    validator = validator
) {

}