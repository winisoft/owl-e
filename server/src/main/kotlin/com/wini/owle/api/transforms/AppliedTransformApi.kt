package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.AppliedTransformDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class AppliedTransformApi(
    dll: DLL<Long, AppliedTransformDto>
) : LongIdEndpointImpl<AppliedTransformDto>(
    dll = dll,
    serializer = AppliedTransformDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform") {
                call.asyncResultList(getAll())
            }
            get("/api/applied-transform/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/applied-transform") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/applied-transform/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/applied-transform") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/applied-transform/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/applied-transform/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/applied-transform/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}