package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.TransformationArgDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TransformationArgApi(
    dll: DLL<Long, TransformationArgDto>,
    validator: (TransformationArgDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TransformationArgDto>(
    dll = dll,
    serializer = TransformationArgDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation-arg") {
                call.asyncResultList(getAll())
            }
            get("/api/transformation-arg/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/transformation-arg") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/transformation-arg/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/transformation-arg") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/transformation-arg/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/transformation-arg/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/transformation-arg/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}