package com.wini.owle.api.typing

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.typing.TypeParameterDll
import com.wini.owle.dto.typing.TypeParameterDto
import io.ktor.server.routing.*

class TypeParameterApi(dll: TypeParameterDll) : GenericLongPkApi<TypeParameterDto>(
    dll = dll,
    clazz = TypeParameterDto::class,
    serializer = TypeParameterDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-parameter") { getAll(context.request.call) }
            get("/api/type-parameter/{id}") { getById(context.request.call) }
            post("/api/type-parameter") { create(context.request.call) }
            post("/api/type-parameter/batch") { createBatch(context.request.call) }
            put("/api/type-parameter") { update(context.request.call) }
            put("/api/type-parameter/batch") { updateBatch(context.request.call) }
            delete("/api/type-parameter/{id}") { super.delete(context.request.call) }
            delete("/api/type-parameter/batch") { deleteBatch(context.request.call) }
        }
    }
}