package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.AppliedTransform
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl

class AppliedTransformApiImpl(
    dll: DLL<Long, AppliedTransform>,
    validator: DtoValidator<AppliedTransform>
): LongIdEndpointImpl<AppliedTransform>(
    dll = dll,
    serializer = AppliedTransform.serializer(),
    validator = validator
), IAppliedTransformApi  {

}