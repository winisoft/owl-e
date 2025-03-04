package com.wini.owle.api.runs

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.runs.StepExecutionDll
import com.wini.owle.dto.StepExecutionDto
import io.ktor.server.routing.*

class StepExecutionApi(dll: StepExecutionDll) : GenericLongPkApi<StepExecutionDto>(
    dll = dll,
    clazz = StepExecutionDto::class,
    serializer = StepExecutionDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step-execution") { getAll(context.request.call) }
            get("/api/step-execution/{id}") { getById(context.request.call) }
            post("/api/step-execution") { create(context.request.call) }
            post("/api/step-execution/batch") { createBatch(context.request.call) }
            put("/api/step-execution") { update(context.request.call) }
            put("/api/step-execution/batch") { updateBatch(context.request.call) }
            delete("/api/step-execution/{id}") { super.delete(context.request.call) }
            delete("/api/step-execution/batch") { deleteBatch(context.request.call) }
        }
    }
}