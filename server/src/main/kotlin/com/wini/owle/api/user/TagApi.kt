package com.wini.owle.api.user

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dto.TagDto
import com.wini.owle.services.user.TagDll
import io.ktor.server.routing.*

class TagApi(dll: TagDll) : GenericLongPkApi<TagDto>(
    dll = dll,
    clazz = TagDto::class,
    serializer = TagDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tag") { getAll(context.request.call) }
            get("/api/tag/{id}") { getById(context.request.call) }
            post("/api/tag") { create(context.request.call) }
            post("/api/tag/batch") { createBatch(context.request.call) }
            put("/api/tag") { update(context.request.call) }
            put("/api/tag/batch") { updateBatch(context.request.call) }
            delete("/api/tag/{id}") { super.delete(context.request.call) }
            delete("/api/tag/batch") { deleteBatch(context.request.call) }
        }
    }
}