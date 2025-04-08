package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.InquisitionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class InquisitionApi(
    dll: DLL<Long, InquisitionDto>
) : LongIdEndpointImpl<InquisitionDto>(
    dll = dll,
    serializer = InquisitionDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/inquisition") {
                call.asyncResultList(getAll())
            }
            get("/api/inquisition/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/inquisition") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/inquisition/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/inquisition") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/inquisition/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/inquisition/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/inquisition/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}