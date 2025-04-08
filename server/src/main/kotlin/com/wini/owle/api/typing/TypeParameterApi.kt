package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeParameterDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TypeParameterApi(
    dll: DLL<Long, TypeParameterDto>,
    validator: (TypeParameterDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TypeParameterDto>(
    dll = dll,
    serializer = TypeParameterDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-parameter") {
                call.asyncResultList(getAll())
            }
            get("/api/type-parameter/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/type-parameter") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/type-parameter/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/type-parameter") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/type-parameter/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/type-parameter/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/type-parameter/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}