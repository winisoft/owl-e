package com.wini.owle.api.transforms

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.transforms.TransformationDll
import com.wini.owle.dto.TransformationDto
import io.ktor.server.routing.*

class TransformationApi(dll: TransformationDll) : GenericLongPkApi<TransformationDto>(
    dll = dll,
    clazz = TransformationDto::class,
    serializer = TransformationDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation") { getAll(context.request.call) }
            get("/api/transformation/{id}") { getById(context.request.call) }
            post("/api/transformation") { create(context.request.call) }
            post("/api/transformation/batch") { createBatch(context.request.call) }
            put("/api/transformation") { update(context.request.call) }
            put("/api/transformation/batch") { updateBatch(context.request.call) }
            delete("/api/transformation/{id}") { super.delete(context.request.call) }
            delete("/api/transformation/batch") { deleteBatch(context.request.call) }
        }
    }
}