package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.Inquisition
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class InquisitionApi(
    dll: DLL<Long, Inquisition>,
    validator: DtoValidator<Inquisition>
): LongIdEndpointImpl<Inquisition>(
    dll = dll,
    serializer = Inquisition.serializer(),
    validator = validator
), IInquisitionApi  {

}