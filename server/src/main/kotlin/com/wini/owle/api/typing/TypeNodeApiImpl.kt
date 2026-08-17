package com.wini.owle.api.typing

import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dto.typing.TypeDeclaration
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator

class TypeNodeApiImpl(
    dll: DLL<Long, TypeDeclaration>,
    validator: DtoValidator<TypeDeclaration>
): ULongIdEndpointImpl<TypeDeclaration>(
    dll = dll,
    serializer = TypeDeclaration.serializer(),
    validator = validator
), ITypeDeclarationApi  {

}