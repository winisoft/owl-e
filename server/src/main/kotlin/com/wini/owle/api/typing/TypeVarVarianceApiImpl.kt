package com.wini.owle.api.typing

import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dto.typing.TypeVariance
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator

class TypeVarVarianceApiImpl(
    dll: DLL<Long, TypeVariance>,
    validator: DtoValidator<TypeVariance>
): ULongIdEndpointImpl<TypeVariance>(
    dll = dll,
    serializer = TypeVariance.serializer(),
    validator = validator
) {

}