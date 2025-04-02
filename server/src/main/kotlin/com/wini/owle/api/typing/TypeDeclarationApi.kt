package com.wini.owle.api.typing

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.typing.TypeDeclarationDll
import com.wini.owle.dto.typing.TypeDeclarationDto
import io.ktor.server.routing.*

class TypeDeclarationApi(dll: TypeDeclarationDll) : GenericLongPkApi<TypeDeclarationDto>(
    dll = dll,
    clazz = TypeDeclarationDto::class,
    serializer = TypeDeclarationDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-declaration") { getAll(context.request.call) }
            get("/api/type-declaration/{id}") { getById(context.request.call) }
            post("/api/type-declaration") { create(context.request.call) }
            post("/api/type-declaration/batch") { createBatch(context.request.call) }
            put("/api/type-declaration") { update(context.request.call) }
            put("/api/type-declaration/batch") { updateBatch(context.request.call) }
            delete("/api/type-declaration/{id}") { super.delete(context.request.call) }
            delete("/api/type-declaration/batch") { deleteBatch(context.request.call) }
        }
    }
}