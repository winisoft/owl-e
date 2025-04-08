package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeDeclarationDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TypeDeclarationApi(
    dll: DLL<Long, TypeDeclarationDto>
) : LongIdEndpointImpl<TypeDeclarationDto>(
    dll = dll,
    serializer = TypeDeclarationDto.serializer(),
    validator = { _-> true }
)  {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-declaration") {
                call.asyncResultList(getAll())
            }
            get("/api/type-declaration/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/type-declaration") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/type-declaration/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/type-declaration") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/type-declaration/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/type-declaration/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/type-declaration/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}