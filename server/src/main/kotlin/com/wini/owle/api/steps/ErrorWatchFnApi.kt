package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatchFn
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class ErrorWatchFnApi(
    dll: DLL<Long, ErrorWatchFn>,
    validator: DtoValidator<ErrorWatchFn>
): LongIdEndpointImpl<ErrorWatchFn>(
    dll = dll,
    serializer = ErrorWatchFn.serializer(),
    validator = validator
), IErrorWatchFnApi  {

}