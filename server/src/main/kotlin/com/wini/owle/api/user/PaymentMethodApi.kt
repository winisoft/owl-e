package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.user.PaymentMethodDll
import com.wini.owle.dto.user.PaymentMethodDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class PaymentMethodApi(
    dll: PaymentMethodDll
) : LongIdEndpointImpl<PaymentMethodDto>(
    dll = dll,
    serializer = PaymentMethodDto.serializer(),
    validator = { _-> true }
) {
    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<PaymentMethodDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/payment-method") {
                call.asyncResult(getAll())
            }
            get("/api/payment-method/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/payment-method") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/payment-method/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/payment-method") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/payment-method/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/payment-method/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/payment-method/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}