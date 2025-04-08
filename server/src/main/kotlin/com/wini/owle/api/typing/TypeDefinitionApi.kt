package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeDefinitionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TypeDefinitionApi(
    dll: DLL<Long, TypeDefinitionDto>
) : LongIdEndpointImpl<TypeDefinitionDto>(
    dll = dll,
    serializer = TypeDefinitionDto.serializer(),
    validator = { _-> true }
)  {


    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-definition") {
                call.asyncResultList(getAll())
            }
            get("/api/type-definition/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/type-definition") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/type-definition/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/type-definition") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/type-definition/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/type-definition/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/type-definition/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}