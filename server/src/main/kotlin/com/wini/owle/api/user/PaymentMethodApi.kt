package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.user.PaymentMethodDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class PaymentMethodApi(
    dll: DLL<Long, PaymentMethodDto>,
    validator: (PaymentMethodDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<PaymentMethodDto>(
    dll = dll,
    serializer = PaymentMethodDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/payment-method") {
                call.asyncResultList(getAll())
            }
            get("/api/payment-method/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/payment-method") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/payment-method/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/payment-method") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/payment-method/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/payment-method/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/payment-method/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}