package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.transforms.AppliedTransformDll
import com.wini.owle.dto.transforms.AppliedTransformDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class AppliedTransformApi(
    dll: AppliedTransformDll
) : LongIdEndpointImpl<AppliedTransformDto>(
    dll = dll,
    serializer = AppliedTransformDto.serializer()
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<AppliedTransformDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform") {
                call.asyncResult(getAll())
            }
            get("/api/applied-transform/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/applied-transform") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/applied-transform/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/applied-transform") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/applied-transform/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/applied-transform/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/applied-transform/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}