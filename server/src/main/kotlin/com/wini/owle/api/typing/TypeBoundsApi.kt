package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.typing.TypeBoundsDll
import com.wini.owle.dto.typing.TypeBoundsDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class TypeBoundsApi(
    dll: TypeBoundsDll,
) : LongIdEndpointImpl<TypeBoundsDto>(
    dll = dll,
    serializer = TypeBoundsDto.serializer(),
    validator = { _-> true }
) {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<TypeBoundsDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-bounds") {
                call.asyncResult(getAll())
            }
            get("/api/type-bounds/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/type-bounds") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/type-bounds/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/type-bounds") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/type-bounds/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/type-bounds/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/type-bounds/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}