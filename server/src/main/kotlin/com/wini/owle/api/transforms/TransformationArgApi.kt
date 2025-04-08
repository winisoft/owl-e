package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.TransformationArgDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TransformationArgApi(
    dll: DLL<Long, TransformationArgDto>
) : LongIdEndpointImpl<TransformationArgDto>(
    dll = dll,
    serializer = TransformationArgDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation-arg") {
                call.asyncResultList(getAll())
            }
            get("/api/transformation-arg/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/transformation-arg") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/transformation-arg/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/transformation-arg") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/transformation-arg/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/transformation-arg/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/transformation-arg/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}