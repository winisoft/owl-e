package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.typing.TypeVarianceDll
import com.wini.owle.dto.typing.TypeVarianceDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class TypeVarianceApi(
    dll: TypeVarianceDll
) : LongIdEndpointImpl<TypeVarianceDto>(
    dll = dll,
    serializer = TypeVarianceDto.serializer(),
    validator = { _-> true }
) {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<TypeVarianceDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }
    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-variance") {
                call.asyncResult(getAll())
            }
            get("/api/type-variance/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/type-variance") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/type-variance/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/type-variance") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/type-variance/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/type-variance/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/type-variance/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}