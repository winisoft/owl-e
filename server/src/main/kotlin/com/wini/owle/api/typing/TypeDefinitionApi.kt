package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.typing.TypeDefinitionDll
import com.wini.owle.dto.typing.TypeDefinitionDto
import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.models.TypeDefinition
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class TypeDefinitionApi(
    dll: TypeDefinitionDll
) : LongIdEndpointImpl<TypeDefinitionDto>(
    dll = dll,
    serializer = TypeDefinitionDto.serializer(),
    validator = { _-> true }
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<TypeDefinitionDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }
    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-definition") {
                call.asyncResult(getAll())
            }
            get("/api/type-definition/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/type-definition") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/type-definition/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/type-definition") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/type-definition/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/type-definition/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/type-definition/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}