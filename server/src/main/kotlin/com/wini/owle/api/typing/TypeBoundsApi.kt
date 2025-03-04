package com.wini.owle.api.typing

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.typing.TypeBoundsDll
import com.wini.owle.dto.TypeBoundsDto
import io.ktor.server.routing.*

class TypeBoundsApi(
    dll: TypeBoundsDll
) : GenericLongPkApi<TypeBoundsDto>(
    dll = dll,
    clazz = TypeBoundsDto::class,
    serializer = TypeBoundsDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-bounds") { getAll(context.request.call) }
            get("/api/type-bounds/{id}") { getById(context.request.call) }
            post("/api/type-bounds") { create(context.request.call) }
            post("/api/type-bounds/batch") { createBatch(context.request.call) }
            put("/api/type-bounds") { update(context.request.call) }
            put("/api/type-bounds/batch") { updateBatch(context.request.call) }
            delete("/api/type-bounds/{id}") { super.delete(context.request.call) }
            delete("/api/type-bounds/batch") { deleteBatch(context.request.call) }
        }
    }
}