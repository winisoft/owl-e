package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.AppliedTransformArgDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class AppliedTransformArgApi(
    dll: DLL<Long, AppliedTransformArgDto>
) : LongIdEndpointImpl<AppliedTransformArgDto>(
    dll = dll,
    serializer = AppliedTransformArgDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform-arg") {
                call.asyncResultList(getAll())
            }
            get("/api/applied-transform-arg/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/applied-transform-arg") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/applied-transform-arg/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/applied-transform-arg") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/applied-transform-arg/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/applied-transform-arg/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/applied-transform-arg/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}