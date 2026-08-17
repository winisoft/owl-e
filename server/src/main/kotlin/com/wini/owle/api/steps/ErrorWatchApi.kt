package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatch
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class ErrorWatchApi(
    dll: DLL<Long, ErrorWatch>,
    validator: DtoValidator<ErrorWatch>
): LongIdEndpointImpl<ErrorWatch>(
    dll = dll,
    serializer = ErrorWatch.serializer(),
    validator = validator
), IErrorWatchApi  {

}