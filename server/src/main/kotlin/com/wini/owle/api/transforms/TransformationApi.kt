package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.TransformationDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TransformationApi(
    dll: DLL<Long, TransformationDto>,
    validator: (TransformationDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TransformationDto>(
    dll = dll,
    serializer = TransformationDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation") {
                call.asyncResultList(getAll())
            }
            get("/api/transformation/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/transformation") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/transformation/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/transformation") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/transformation/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/transformation/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/transformation/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}