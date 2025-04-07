package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.typing.TypeSchemaRefDll
import com.wini.owle.dto.typing.TypeSchemaRefDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.dll.DLL


class TypeSchemaRefApi(
    dll: DLL<Long, TypeSchemaRefDto>
) : LongIdEndpointImpl<TypeSchemaRefDto>(
    dll = dll,
    serializer = TypeSchemaRefDto.serializer(),
    validator = { _-> true }
) {


    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-schema-ref") {
                call.asyncResultMany(getAll())
            }
            get("/api/type-schema-ref/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/type-schema-ref") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/type-schema-ref/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/type-schema-ref") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/type-schema-ref/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/type-schema-ref/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/type-schema-ref/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}