package com.wini.owle.api.workflows

import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Grouper
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.ULongIdEndpointImpl

class GrouperApiImpl(
    dll: DLL<ULong, Grouper>,
    validator: DtoValidator<Grouper>,
    override val endpointPath: String
): ULongIdEndpointImpl<Grouper>(
    dll = dll,
    serializer = Grouper.serializer(),
    validator = validator
) {

}