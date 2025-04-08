package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeBoundsDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class TypeBoundsApi(
    dll: DLL<Long, TypeBoundsDto>,
    validator: (TypeBoundsDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TypeBoundsDto>(
    dll = dll,
    serializer = TypeBoundsDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-bounds") {
                call.asyncResultList(getAll())
            }
            get("/api/type-bounds/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/type-bounds") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/type-bounds/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/type-bounds") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/type-bounds/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/type-bounds/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/type-bounds/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}