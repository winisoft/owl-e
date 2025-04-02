package com.wini.owle.api.steps

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.steps.ProvisionDll
import com.wini.owle.dto.steps.ProvisionDto
import io.ktor.server.routing.*

class ProvisionApi(dll: ProvisionDll): GenericLongPkApi<ProvisionDto>(
    dll = dll,
    clazz = ProvisionDto::class,
    serializer = ProvisionDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/provision") { getAll(context.request.call) }
            get("/api/provision/{id}") { getById(context.request.call) }
            post("/api/provision") { create(context.request.call) }
            post("/api/provision/batch") { createBatch(context.request.call) }
            put("/api/provision") { update(context.request.call) }
            put("/api/provision/batch") { updateBatch(context.request.call) }
            delete("/api/provision/{id}") { super.delete(context.request.call) }
            delete("/api/provision/batch") { deleteBatch(context.request.call) }
        }
    }
}