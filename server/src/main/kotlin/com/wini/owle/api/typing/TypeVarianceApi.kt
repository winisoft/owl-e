package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeVarianceDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TypeVarianceApi(
    dll: DLL<Long, TypeVarianceDto>,
    validator: (TypeVarianceDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TypeVarianceDto>(
    dll = dll,
    serializer = TypeVarianceDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-variance") {
                call.asyncResultList(getAll())
            }
            get("/api/type-variance/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/type-variance") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/type-variance/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/type-variance") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/type-variance/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/type-variance/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/type-variance/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}