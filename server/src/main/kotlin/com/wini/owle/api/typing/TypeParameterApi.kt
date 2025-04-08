package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeParameterDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
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
                call.asyncResultList(getAll())
            }
            get("/api/type-parameter/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/type-parameter") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/type-parameter/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/type-parameter") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/type-parameter/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/type-parameter/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/type-parameter/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}