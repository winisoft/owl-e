package com.wini.owle.api.runs

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.runs.ExecutionLogDll
import com.wini.owle.dto.ExecutionLogDto
import io.ktor.server.routing.*

class ExecutionLogApi(dll: ExecutionLogDll) : GenericLongPkApi<ExecutionLogDto>(
    dll = dll,
    clazz = ExecutionLogDto::class,
    serializer = ExecutionLogDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/execution-log") { getAll(context.request.call) }
            get("/api/execution-log/{id}") { getById(context.request.call) }
            post("/api/execution-log") { create(context.request.call) }
            post("/api/execution-log/batch") { createBatch(context.request.call) }
            put("/api/execution-log") { update(context.request.call) }
            put("/api/execution-log/batch") { updateBatch(context.request.call) }
            delete("/api/execution-log/{id}") { super.delete(context.request.call) }
            delete("/api/execution-log/batch") { deleteBatch(context.request.call) }
        }
    }
}