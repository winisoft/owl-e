package com.wini.owle.api.workflows

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.workflows.PhaseDll
import com.wini.owle.dto.PhaseDto
import io.ktor.server.routing.*

class PhaseApi(dll: PhaseDll) : GenericLongPkApi<PhaseDto>(
    dll = dll,
    clazz = PhaseDto::class,
    serializer = PhaseDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/phase") { getAll(context.request.call) }
            get("/api/phase/{id}") { getById(context.request.call) }
            post("/api/phase") { create(context.request.call) }
            post("/api/phase/batch") { createBatch(context.request.call) }
            put("/api/phase") { update(context.request.call) }
            put("/api/phase/batch") { updateBatch(context.request.call) }
            delete("/api/phase/{id}") { super.delete(context.request.call) }
            delete("/api/phase/batch") { deleteBatch(context.request.call) }
        }
    }
}