package com.wini.owle.api.typing

import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dto.typing.TypeSchemaRef
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TypeSchemaRefApi(
    dll: DLL<Long, TypeSchemaRef>,
    validator: DtoValidator<TypeSchemaRef>
): ULongIdEndpointImpl<TypeSchemaRef>(
    dll = dll,
    serializer = TypeSchemaRef.serializer(),
    validator = validator
) {


    
}