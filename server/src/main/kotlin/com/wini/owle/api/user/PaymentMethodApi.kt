package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.user.PaymentMethodDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class PaymentMethodApi(
    dll: DLL<Long, PaymentMethodDto>
) : LongIdEndpointImpl<PaymentMethodDto>(
    dll = dll,
    serializer = PaymentMethodDto.serializer(),
    validator = { _-> true }
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/payment-method") {
                call.asyncResultList(getAll())
            }
            get("/api/payment-method/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/payment-method") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/payment-method/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/payment-method") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/payment-method/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/payment-method/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/payment-method/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}