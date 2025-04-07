package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.steps.InquisitionDll
import com.wini.owle.dto.steps.InquisitionDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class InquisitionApi(
    dll: InquisitionDll
) : LongIdEndpointImpl<InquisitionDto>(
    dll = dll,
    serializer = InquisitionDto.serializer()
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<InquisitionDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }
    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/inquisition") {
                call.asyncResult(getAll())
            }
            get("/api/inquisition/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/inquisition") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/inquisition/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/inquisition") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/inquisition/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/inquisition/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/inquisition/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}