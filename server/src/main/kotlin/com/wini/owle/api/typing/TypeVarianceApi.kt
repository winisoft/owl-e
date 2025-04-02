package com.wini.owle.api.typing

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.typing.TypeVarianceDll
import com.wini.owle.dto.typing.TypeVarianceDto
import io.ktor.server.routing.*

class TypeVarianceApi(dll: TypeVarianceDll) : GenericLongPkApi<TypeVarianceDto>(
    dll = dll,
    clazz = TypeVarianceDto::class,
    serializer = TypeVarianceDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-variance") { getAll(context.request.call) }
            get("/api/type-variance/{id}") { getById(context.request.call) }
            post("/api/type-variance") { create(context.request.call) }
            post("/api/type-variance/batch") { createBatch(context.request.call) }
            put("/api/type-variance") { update(context.request.call) }
            put("/api/type-variance/batch") { updateBatch(context.request.call) }
            delete("/api/type-variance/{id}") { super.delete(context.request.call) }
            delete("/api/type-variance/batch") { deleteBatch(context.request.call) }
        }
    }
}