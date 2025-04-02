package com.wini.owle.api.steps

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.workflows.ToolParamDll
import com.wini.owle.dto.workflows.ToolParamDto
import io.ktor.server.routing.*

class ToolParamApi(dll: ToolParamDll) : GenericLongPkApi<ToolParamDto>(
    dll = dll,
    clazz = ToolParamDto::class,
    serializer = ToolParamDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-param") { getAll(context.request.call) }
            get("/api/tool-param/{id}") { getById(context.request.call) }
            post("/api/tool-param") { create(context.request.call) }
            post("/api/tool-param/batch") { createBatch(context.request.call) }
            put("/api/tool-param") { update(context.request.call) }
            put("/api/tool-param/batch") { updateBatch(context.request.call) }
            delete("/api/tool-param/{id}") { super.delete(context.request.call) }
            delete("/api/tool-param/batch") { deleteBatch(context.request.call) }
        }
    }
}