package com.wini.owle.api.steps

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.steps.RevisionDll
import com.wini.owle.dto.steps.RevisionDto
import io.ktor.server.routing.*

class RevisionApi(dll: RevisionDll): GenericLongPkApi<RevisionDto>(
    dll = dll,
    clazz = RevisionDto::class,
    serializer = RevisionDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/revision") { getAll(context.request.call) }
            get("/api/revision/{id}") { getById(context.request.call) }
            post("/api/revision") { create(context.request.call) }
            post("/api/revision/batch") { createBatch(context.request.call) }
            put("/api/revision") { update(context.request.call) }
            put("/api/revision/batch") { updateBatch(context.request.call) }
            delete("/api/revision/{id}") { super.delete(context.request.call) }
            delete("/api/revision/batch") { deleteBatch(context.request.call) }
        }
    }
}