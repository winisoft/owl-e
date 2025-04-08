package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeDeclarationDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TypeDeclarationApi(
    dll: DLL<Long, TypeDeclarationDto>,
    validator: (TypeDeclarationDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TypeDeclarationDto>(
    dll = dll,
    serializer = TypeDeclarationDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-declaration") {
                call.asyncResultList(getAll())
            }
            get("/api/type-declaration/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/type-declaration") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/type-declaration/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/type-declaration") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/type-declaration/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/type-declaration/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/type-declaration/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}