package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeSchemaRefDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
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
                call.asyncResultList(getAll())
            }
            get("/api/type-schema-ref/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/type-schema-ref") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/type-schema-ref/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/type-schema-ref") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/type-schema-ref/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/type-schema-ref/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/type-schema-ref/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}