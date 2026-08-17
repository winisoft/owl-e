package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.TransformArg
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl

class TransformationArgApiImpl(
    dll: DLL<Long, TransformArg>,
    validator: DtoValidator<TransformArg>
): LongIdEndpointImpl<TransformArg>(
    dll = dll,
    serializer = TransformArg.serializer(),
    validator = validator
), ITransformationArgApi  {

}