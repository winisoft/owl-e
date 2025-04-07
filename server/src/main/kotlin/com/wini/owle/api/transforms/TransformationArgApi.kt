package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.transforms.TransformationArgDll
import com.wini.owle.dto.transforms.TransformationArgDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class TransformationArgApi(
    dll: TransformationArgDll
) : LongIdEndpointImpl<TransformationArgDto>(
    dll = dll,
    serializer = TransformationArgDto.serializer()
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<TransformationArgDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }
    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation-arg") {
                call.asyncResult(getAll())
            }
            get("/api/transformation-arg/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/transformation-arg") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/transformation-arg/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/transformation-arg") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/transformation-arg/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/transformation-arg/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/transformation-arg/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}