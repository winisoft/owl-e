package com.wini.owle.api.workflows

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.workflows.DataPointDll
import com.wini.owle.dto.workflows.DataPointDto
import io.ktor.server.routing.*

class DataPointsApi(
        dll: DataPointDll
) : GenericLongPkApi<DataPointDto>(
    dll = dll,
    clazz = DataPointDto::class,
    serializer = DataPointDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/data-points") { getAll(context.request.call) }
            get("/api/data-points/{id}") { getById(context.request.call) }
            post("/api/data-points") { create(context.request.call) }
            post("/api/data-points/batch") { createBatch(context.request.call) }
            put("/api/data-points") { update(context.request.call) }
            put("/api/data-points/batch") { updateBatch(context.request.call) }
            delete("/api/data-points/{id}") { super.delete(context.request.call) }
            delete("/api/data-points/batch") { deleteBatch(context.request.call) }
        }
    }
}