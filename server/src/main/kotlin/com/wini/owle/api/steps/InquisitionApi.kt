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
                call.asyncResultMany(getAll())
            }
            get("/api/inquisition/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/inquisition") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/inquisition/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/inquisition") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/inquisition/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/inquisition/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/inquisition/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}