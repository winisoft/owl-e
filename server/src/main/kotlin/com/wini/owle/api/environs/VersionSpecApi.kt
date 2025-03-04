package com.wini.owle.api.environs

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.environs.VersionSpecDll
import com.wini.owle.dto.VersionSpecDto
import io.ktor.server.routing.*

class VersionSpecApi(dll: VersionSpecDll) : GenericLongPkApi<VersionSpecDto>(
    dll = dll,
    clazz = VersionSpecDto::class,
    serializer = VersionSpecDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/version-spec") { getAll(context.request.call) }
            get("/api/version-spec/{id}") { getById(context.request.call) }
            post("/api/version-spec") { create(context.request.call) }
            post("/api/version-spec/batch") { createBatch(context.request.call) }
            put("/api/version-spec") { update(context.request.call) }
            put("/api/version-spec/batch") { updateBatch(context.request.call) }
            delete("/api/version-spec/{id}") { super.delete(context.request.call) }
            delete("/api/version-spec/batch") { deleteBatch(context.request.call) }
        }
    }
}