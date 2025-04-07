package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.steps.ProvisionDll
import com.wini.owle.dto.steps.ProvisionDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class ProvisionApi(
    dll: ProvisionDll
): LongIdEndpointImpl<ProvisionDto>(
    dll = dll,
    serializer = ProvisionDto.serializer()
) {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<ProvisionDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    //"") { getAll(context.request.call) }
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/provision") {
                call.asyncResult(getAll())
            }
            get("/api/provision/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/provision") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/provision/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/provision") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/provision/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/provision/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/provision/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}