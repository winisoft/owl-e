package com.wini.owle.api.typing

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.typing.TypeSchemaRefDll
import com.wini.owle.dto.typing.TypeSchemaRefDto
import io.ktor.server.routing.*

class TypeSchemaRefApi(dll: TypeSchemaRefDll) : GenericLongPkApi<TypeSchemaRefDto>(
    dll = dll,
    clazz = TypeSchemaRefDto::class,
    serializer = TypeSchemaRefDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-schema-ref") { getAll(context.request.call) }
            get("/api/type-schema-ref/{id}") { getById(context.request.call) }
            post("/api/type-schema-ref") { create(context.request.call) }
            post("/api/type-schema-ref/batch") { createBatch(context.request.call) }
            put("/api/type-schema-ref") { update(context.request.call) }
            put("/api/type-schema-ref/batch") { updateBatch(context.request.call) }
            delete("/api/type-schema-ref/{id}") { super.delete(context.request.call) }
            delete("/api/type-schema-ref/batch") { deleteBatch(context.request.call) }
        }
    }
}