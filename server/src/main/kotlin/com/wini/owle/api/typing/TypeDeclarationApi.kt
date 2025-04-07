package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.typing.TypeDeclarationDll
import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class TypeDeclarationApi(
    dll: TypeDeclarationDll
) : LongIdEndpointImpl<TypeDeclarationDto>(
    dll = dll,
    serializer = TypeDeclarationDto.serializer(),
    validator = { _-> true }
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<TypeDeclarationDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-declaration") {
                call.asyncResult(getAll())
            }
            get("/api/type-declaration/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/type-declaration") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/type-declaration/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/type-declaration") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/type-declaration/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/type-declaration/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/type-declaration/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}