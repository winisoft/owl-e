package com.wini.owle.api.environs

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.environs.PackageDll
import com.wini.owle.dto.PackageDto
import io.ktor.server.routing.*

class PackageApi(dll: PackageDll): GenericLongPkApi<PackageDto>(
    dll = dll,
    clazz = PackageDto::class,
    serializer = PackageDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/package") { getAll(context.request.call) }
            get("/api/package/{id}") { getById(context.request.call) }
            post("/api/package") { create(context.request.call) }
            post("/api/package/batch") { createBatch(context.request.call) }
            put("/api/package") { update(context.request.call) }
            put("/api/package/batch") { updateBatch(context.request.call) }
            delete("/api/package/{id}") { super.delete(context.request.call) }
            delete("/api/package/batch") { deleteBatch(context.request.call) }
        }
    }

}