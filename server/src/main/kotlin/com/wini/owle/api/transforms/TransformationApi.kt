package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.transforms.TransformationDll
import com.wini.owle.dto.transforms.TransformationDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class TransformationApi(
    dll: TransformationDll
) : LongIdEndpointImpl<TransformationDto>(
    dll = dll,
    serializer = TransformationDto.serializer()
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<TransformationDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation") {
                call.asyncResult(getAll())
            }
            get("/api/transformation/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/transformation") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/transformation/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/transformation") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/transformation/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/transformation/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/transformation/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}