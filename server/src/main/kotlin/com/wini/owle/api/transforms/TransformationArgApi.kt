package com.wini.owle.api.transforms

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.transforms.TransformationArgDll
import com.wini.owle.dto.TransformationArgDto
import io.ktor.server.routing.*

class TransformationArgApi(dll: TransformationArgDll) : GenericLongPkApi<TransformationArgDto>(
    dll = dll,
    clazz = TransformationArgDto::class,
    serializer = TransformationArgDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation-arg") { getAll(context.request.call) }
            get("/api/transformation-arg/{id}") { getById(context.request.call) }
            post("/api/transformation-arg") { create(context.request.call) }
            post("/api/transformation-arg/batch") { createBatch(context.request.call) }
            put("/api/transformation-arg") { update(context.request.call) }
            put("/api/transformation-arg/batch") { updateBatch(context.request.call) }
            delete("/api/transformation-arg/{id}") { super.delete(context.request.call) }
            delete("/api/transformation-arg/batch") { deleteBatch(context.request.call) }
        }
    }
}