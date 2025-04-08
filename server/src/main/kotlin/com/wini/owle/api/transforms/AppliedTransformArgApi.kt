package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.AppliedTransformArgDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class AppliedTransformArgApi(
    dll: DLL<Long, AppliedTransformArgDto>,
    validator: (AppliedTransformArgDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<AppliedTransformArgDto>(
    dll = dll,
    serializer = AppliedTransformArgDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform-arg") {
                call.asyncResultList(getAll())
            }
            get("/api/applied-transform-arg/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/applied-transform-arg") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/applied-transform-arg/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/applied-transform-arg") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/applied-transform-arg/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/applied-transform-arg/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/applied-transform-arg/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}