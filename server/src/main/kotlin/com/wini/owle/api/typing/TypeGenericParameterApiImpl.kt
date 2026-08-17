package com.wini.owle.api.typing

import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dto.typing.TypeParameter
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator

class TypeGenericParameterApiImpl(
    dll: DLL<ULong, TypeParameter>,
    endpointPath: String,
    validator: DtoValidator<TypeParameter>
): ULongIdEndpointImpl<TypeParameter>(
    dll = dll,
    endpointPath = endpointPath,
    serializer = TypeParameter.serializer(),
    validator = validator
), TypeParameterApi  {

}