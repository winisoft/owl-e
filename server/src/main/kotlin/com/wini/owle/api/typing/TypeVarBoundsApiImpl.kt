package com.wini.owle.api.typing

import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dll.DLL
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.entity.typing.TypeVarBounds

import com.wini.owle.validators.DtoValidator

class TypeVarBoundsApiImpl(
    dll: DLL<ULong, TypeVarBound>,
    validator: DtoValidator<TypeVarBound>,
    override val endpointPath: String
): ULongIdEndpointImpl<TypeVarBound>(
    dll = dll,
    serializer = TypeVarBound.serializer(),
    endpointPath = endpointPath,
    validator = validator
) {

}