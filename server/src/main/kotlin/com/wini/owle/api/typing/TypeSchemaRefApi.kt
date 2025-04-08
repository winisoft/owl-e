package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeSchemaRefDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TypeSchemaRefApi(
    dll: DLL<Long, TypeSchemaRefDto>,
    validator: (TypeSchemaRefDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TypeSchemaRefDto>(
    dll = dll,
    serializer = TypeSchemaRefDto.serializer(),
    validator = validator
) {


    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-schema-ref") {
                call.asyncResultList(getAll())
            }
            get("/api/type-schema-ref/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/type-schema-ref") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/type-schema-ref/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/type-schema-ref") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/type-schema-ref/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/type-schema-ref/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/type-schema-ref/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}