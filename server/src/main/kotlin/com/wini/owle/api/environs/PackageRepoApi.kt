package com.wini.owle.api.environs

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.environs.PackageRepoDll
import com.wini.owle.dto.environs.PackageRepoDto
import io.ktor.server.routing.*

class PackageRepoApi(dll: PackageRepoDll): GenericLongPkApi<PackageRepoDto>(
    dll = dll,
    clazz = PackageRepoDto::class,
    serializer = PackageRepoDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/package-repo") { getAll(context.request.call) }
            get("/api/package-repo/{id}") { getById(context.request.call) }
            post("/api/package-repo") { create(context.request.call) }
            post("/api/package-repo/batch") { createBatch(context.request.call) }
            put("/api/package-repo") { update(context.request.call) }
            put("/api/package-repo/batch") { updateBatch(context.request.call) }
            delete("/api/package-repo/{id}") { super.delete(context.request.call) }
            delete("/api/package-repo/batch") { deleteBatch(context.request.call) }
        }
    }
}