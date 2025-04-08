package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.AppliedTransformDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class AppliedTransformApi(
    dll: DLL<Long, AppliedTransformDto>,
    validator: (AppliedTransformDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<AppliedTransformDto>(
    dll = dll,
    serializer = AppliedTransformDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform") {
                call.asyncResultList(getAll())
            }
            get("/api/applied-transform/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/applied-transform") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/applied-transform/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/applied-transform") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/applied-transform/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/applied-transform/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/applied-transform/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}