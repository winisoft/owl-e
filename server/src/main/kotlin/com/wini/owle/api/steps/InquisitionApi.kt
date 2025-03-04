package com.wini.owle.api.steps

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.steps.InquisitionDll
import com.wini.owle.dto.InquisitionDto
import io.ktor.server.routing.*

class InquisitionApi(dll: InquisitionDll) : GenericLongPkApi<InquisitionDto>(
    dll = dll,
    clazz = InquisitionDto::class,
    serializer = InquisitionDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/inquisition") { getAll(context.request.call) }
            get("/api/inquisition/{id}") { getById(context.request.call) }
            post("/api/inquisition") { create(context.request.call) }
            post("/api/inquisition/batch") { createBatch(context.request.call) }
            put("/api/inquisition") { update(context.request.call) }
            put("/api/inquisition/batch") { updateBatch(context.request.call) }
            delete("/api/inquisition/{id}") { super.delete(context.request.call) }
            delete("/api/inquisition/batch") { deleteBatch(context.request.call) }
        }
    }
}