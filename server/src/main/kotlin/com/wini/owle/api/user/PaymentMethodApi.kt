package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.user.PaymentMethodDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class PaymentMethodApi(
    dll: DLL<Long, PaymentMethodDto>,
    validator: DtoValidator<PaymentMethodDto>
): LongIdEndpointImpl<PaymentMethodDto>(
    dll = dll,
    serializer = PaymentMethodDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/payment-method") {
                call.execute(OwleApiCall.GetMany(PaymentMethodDto::class))
            }
            get("/api/payment-method/{id}") {
                call.execute(OwleApiCall.GetOne(PaymentMethodDto::class))
            }
            post("/api/payment-method") {
                call.execute(OwleApiCall.CreateOne(PaymentMethodDto::class))
            }
            post("/api/payment-method/batch") {
                call.execute(OwleApiCall.CreateMany(PaymentMethodDto::class))
            }
            put("/api/payment-method") {
                call.execute(OwleApiCall.UpdateOne(PaymentMethodDto::class))
            }
            put("/api/payment-method/batch") {
                call.execute(OwleApiCall.UpdateMany(PaymentMethodDto::class))
            }
            delete("/api/payment-method/{id}") {
                call.execute(OwleApiCall.DeleteOne(PaymentMethodDto::class)) 
            }
            delete("/api/payment-method/batch") {
                call.execute(OwleApiCall.DeleteMany(PaymentMethodDto::class))
            }
        }
    }
}