package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.Provision
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class ProvisionApi(
    dll: DLL<Long, Provision>,
    validator: DtoValidator<Provision>
): LongIdEndpointImpl<Provision>(
    dll = dll,
    serializer = Provision.serializer(),
    validator = validator
) {

}