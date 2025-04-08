package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.TransformationDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TransformationApi(
    dll: DLL<Long, TransformationDto>
) : LongIdEndpointImpl<TransformationDto>(
    dll = dll,
    serializer = TransformationDto.serializer()
)  {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation") {
                call.asyncResultList(getAll())
            }
            get("/api/transformation/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/transformation") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/transformation/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/transformation") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/transformation/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/transformation/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/transformation/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}