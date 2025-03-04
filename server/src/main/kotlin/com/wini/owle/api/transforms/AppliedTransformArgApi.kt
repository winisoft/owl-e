package com.wini.owle.api.transforms

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.transforms.AppliedTransformArgDll
import com.wini.owle.dto.AppliedTransformArgDto
import io.ktor.server.routing.*

class AppliedTransformArgApi(dll: AppliedTransformArgDll) : GenericLongPkApi<AppliedTransformArgDto>(
    dll = dll,
    clazz = AppliedTransformArgDto::class,
    serializer = AppliedTransformArgDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform-arg") { getAll(context.request.call) }
            get("/api/applied-transform-arg/{id}") { getById(context.request.call) }
            post("/api/applied-transform-arg") { create(context.request.call) }
            post("/api/applied-transform-arg/batch") { createBatch(context.request.call) }
            put("/api/applied-transform-arg") { update(context.request.call) }
            put("/api/applied-transform-arg/batch") { updateBatch(context.request.call) }
            delete("/api/applied-transform-arg/{id}") { super.delete(context.request.call) }
            delete("/api/applied-transform-arg/batch") { deleteBatch(context.request.call) }
        }
    }
}