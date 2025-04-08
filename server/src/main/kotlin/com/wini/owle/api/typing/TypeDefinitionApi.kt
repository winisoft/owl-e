package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeDefinitionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TypeDefinitionApi(
    dll: DLL<Long, TypeDefinitionDto>,
    validator: (TypeDefinitionDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TypeDefinitionDto>(
    dll = dll,
    serializer = TypeDefinitionDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-definition") {
                call.asyncResultList(getAll())
            }
            get("/api/type-definition/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/type-definition") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/type-definition/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/type-definition") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/type-definition/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/type-definition/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/type-definition/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}