package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.transforms.AppliedTransformArgDll
import com.wini.owle.dto.transforms.AppliedTransformArgDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class AppliedTransformArgApi(
    dll: AppliedTransformArgDll
) : LongIdEndpointImpl<AppliedTransformArgDto>(
    dll = dll,
    serializer = AppliedTransformArgDto.serializer()
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<AppliedTransformArgDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }
    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform-arg") {
                call.asyncResult(getAll())
            }
            get("/api/applied-transform-arg/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/applied-transform-arg") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/applied-transform-arg/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/applied-transform-arg") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/applied-transform-arg/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/applied-transform-arg/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/applied-transform-arg/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}