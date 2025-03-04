package com.wini.owle.api.steps

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.workflows.ToolDll
import com.wini.owle.dto.ToolDto
import io.ktor.server.routing.*

class ToolApi(dll: ToolDll) : GenericLongPkApi<ToolDto>(
    dll = dll,
    clazz = ToolDto::class,
    serializer = ToolDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool") { getAll(context.request.call) }
            get("/api/tool/{id}") { getById(context.request.call) }
            post("/api/tool") { create(context.request.call) }
            post("/api/tool/batch") { createBatch(context.request.call) }
            put("/api/tool") { update(context.request.call) }
            put("/api/tool/batch") { updateBatch(context.request.call) }
            delete("/api/tool/{id}") { super.delete(context.request.call) }
            delete("/api/tool/batch") { deleteBatch(context.request.call) }
        }
    }
}