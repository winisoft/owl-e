package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.typing.TypeParameterDll
import com.wini.owle.dto.typing.TypeParameterDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.dll.DLL


class TypeParameterApi(
    dll: DLL<Long, TypeParameterDto>
) : LongIdEndpointImpl<TypeParameterDto>(
    dll = dll,
    serializer = TypeParameterDto.serializer(),
    validator = { _-> true }
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-parameter") {
                call.asyncResultMany(getAll())
            }
            get("/api/type-parameter/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/type-parameter") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/type-parameter/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/type-parameter") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/type-parameter/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/type-parameter/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/type-parameter/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}