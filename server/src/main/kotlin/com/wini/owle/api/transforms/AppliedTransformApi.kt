package com.wini.owle.api.transforms

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.transforms.AppliedTransformDll
import com.wini.owle.dto.transforms.AppliedTransformDto
import io.ktor.server.routing.*

class AppliedTransformApi(dll: AppliedTransformDll) : GenericLongPkApi<AppliedTransformDto>(
    dll = dll,
    clazz = AppliedTransformDto::class,
    serializer = AppliedTransformDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform") { getAll(context.request.call) }
            get("/api/applied-transform/{id}") { getById(context.request.call) }
            post("/api/applied-transform") { create(context.request.call) }
            post("/api/applied-transform/batch") { createBatch(context.request.call) }
            put("/api/applied-transform") { update(context.request.call) }
            put("/api/applied-transform/batch") { updateBatch(context.request.call) }
            delete("/api/applied-transform/{id}") { super.delete(context.request.call) }
            delete("/api/applied-transform/batch") { deleteBatch(context.request.call) }
        }
    }
}