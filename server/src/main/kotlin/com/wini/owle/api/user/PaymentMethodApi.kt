package com.wini.owle.api.user

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.user.PaymentMethodDll
import com.wini.owle.dto.user.PaymentMethodDto
import io.ktor.server.routing.*

class PaymentMethodApi(dll: PaymentMethodDll) : GenericLongPkApi<PaymentMethodDto>(
    dll = dll,
    clazz = PaymentMethodDto::class,
    serializer = PaymentMethodDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/payment-method") { getAll(context.request.call) }
            get("/api/payment-method/{id}") { getById(context.request.call) }
            post("/api/payment-method") { create(context.request.call) }
            post("/api/payment-method/batch") { createBatch(context.request.call) }
            put("/api/payment-method") { update(context.request.call) }
            put("/api/payment-method/batch") { updateBatch(context.request.call) }
            delete("/api/payment-method/{id}") { super.delete(context.request.call) }
            delete("/api/payment-method/batch") { deleteBatch(context.request.call) }
        }
    }
}