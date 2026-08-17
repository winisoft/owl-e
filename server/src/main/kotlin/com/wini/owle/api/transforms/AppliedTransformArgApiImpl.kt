package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.AppliedTransformArg
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl

class AppliedTransformArgApiImpl(
    dll: DLL<Long, AppliedTransformArg>,
    validator: DtoValidator<AppliedTransformArg>
): LongIdEndpointImpl<AppliedTransformArg>(
    dll = dll,
    serializer = AppliedTransformArg.serializer(),
    validator = validator
) {

}