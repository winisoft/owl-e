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
), PaymentMethodApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/payment-method") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/payment-method/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/payment-method") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/payment-method/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/payment-method") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/payment-method/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/payment-method/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/payment-method/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}