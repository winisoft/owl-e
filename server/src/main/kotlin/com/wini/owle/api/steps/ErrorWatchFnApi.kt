package com.wini.owle.api.steps

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.steps.ErrorWatchFnDll
import com.wini.owle.dto.ErrorWatchFnDto
import io.ktor.server.routing.*

class ErrorWatchFnApi(dll: ErrorWatchFnDll) : GenericLongPkApi<ErrorWatchFnDto>(
    dll = dll,
    clazz = ErrorWatchFnDto::class,
    serializer = ErrorWatchFnDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/error-watch-fn") { getAll(context.request.call) }
            get("/api/error-watch-fn/{id}") { getById(context.request.call) }
            post("/api/error-watch-fn") { create(context.request.call) }
            post("/api/error-watch-fn/batch") { createBatch(context.request.call) }
            put("/api/error-watch-fn") { update(context.request.call) }
            put("/api/error-watch-fn/batch") { updateBatch(context.request.call) }
            delete("/api/error-watch-fn/{id}") { super.delete(context.request.call) }
            delete("/api/error-watch-fn/batch") { deleteBatch(context.request.call) }
        }
    }
}