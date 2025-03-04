package com.wini.owle.api.steps

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.workflows.StepDll
import com.wini.owle.dto.StepDto
import io.ktor.server.routing.*

class StepApi(dll: StepDll) : GenericLongPkApi<StepDto>(
    dll = dll,
    clazz = StepDto::class,
    serializer = StepDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step") { getAll(context.request.call) }
            get("/api/step/{id}") { getById(context.request.call) }
            post("/api/step") { create(context.request.call) }
            post("/api/step/batch") { createBatch(context.request.call) }
            put("/api/step") { update(context.request.call) }
            put("/api/step/batch") { updateBatch(context.request.call) }
            delete("/api/step/{id}") { super.delete(context.request.call) }
            delete("/api/step/batch") { deleteBatch(context.request.call) }
        }
    }
}