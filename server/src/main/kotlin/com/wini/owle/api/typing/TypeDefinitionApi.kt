package com.wini.owle.api.typing

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.typing.TypeDefinitionDll
import com.wini.owle.dto.typing.TypeDefinitionDto
import io.ktor.server.routing.*

class TypeDefinitionApi(dll: TypeDefinitionDll) : GenericLongPkApi<TypeDefinitionDto>(
    dll = dll,
    clazz = TypeDefinitionDto::class,
    serializer = TypeDefinitionDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-definition") { getAll(context.request.call) }
            get("/api/type-definition/{id}") { getById(context.request.call) }
            post("/api/type-definition") { create(context.request.call) }
            post("/api/type-definition/batch") { createBatch(context.request.call) }
            put("/api/type-definition") { update(context.request.call) }
            put("/api/type-definition/batch") { updateBatch(context.request.call) }
            delete("/api/type-definition/{id}") { super.delete(context.request.call) }
            delete("/api/type-definition/batch") { deleteBatch(context.request.call) }
        }
    }
}