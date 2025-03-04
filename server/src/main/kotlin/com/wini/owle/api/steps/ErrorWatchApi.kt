package com.wini.owle.api.steps

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.steps.ErrorWatchDll
import com.wini.owle.dto.ErrorWatchDto
import io.ktor.server.routing.*

class ErrorWatchApi(dll: ErrorWatchDll) : GenericLongPkApi<ErrorWatchDto>(
    dll = dll,
    clazz = ErrorWatchDto::class,
    serializer = ErrorWatchDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/error-watch") { getAll(context.request.call) }
            get("/api/error-watch/{id}") { getById(context.request.call) }
            post("/api/error-watch") { create(context.request.call) }
            post("/api/error-watch/batch") { createBatch(context.request.call) }
            put("/api/error-watch") { update(context.request.call) }
            put("/api/error-watch/batch") { updateBatch(context.request.call) }
            delete("/api/error-watch/{id}") { super.delete(context.request.call) }
            delete("/api/error-watch/batch") { deleteBatch(context.request.call) }
        }
    }
}