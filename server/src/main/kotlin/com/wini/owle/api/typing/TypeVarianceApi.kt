package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeVarianceDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TypeVarianceApi(
    dll: DLL<Long, TypeVarianceDto>
) : LongIdEndpointImpl<TypeVarianceDto>(
    dll = dll,
    serializer = TypeVarianceDto.serializer(),
    validator = { _-> true }
) {


    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-variance") {
                call.asyncResultList(getAll())
            }
            get("/api/type-variance/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/type-variance") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/type-variance/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/type-variance") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/type-variance/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/type-variance/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/type-variance/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}