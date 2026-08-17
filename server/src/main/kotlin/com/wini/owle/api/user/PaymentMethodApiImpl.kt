package com.wini.owle.api.user

import com.wini.owle.dto.user.PaymentMethod
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.ULongIdEndpointImpl


class PaymentMethodApiImpl(
    dll: DLL<ULong, PaymentMethod>,
    validator: DtoValidator<PaymentMethod>,
    override val endpointPath: String
): ULongIdEndpointImpl<PaymentMethod>(
    dll = dll,
    serializer = PaymentMethod.serializer(),
    endpointPath = endpointPath,
    validator = validator
) {

}