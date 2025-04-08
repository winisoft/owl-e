package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.InquisitionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class InquisitionApi(
    dll: DLL<Long, InquisitionDto>,
    validator: (InquisitionDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<InquisitionDto>(
    dll = dll,
    serializer = InquisitionDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/inquisition") {
                call.asyncResultList(getAll())
            }
            get("/api/inquisition/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/inquisition") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/inquisition/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/inquisition") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/inquisition/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/inquisition/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/inquisition/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}