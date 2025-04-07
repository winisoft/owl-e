package com.wini.owle.api.environs

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.mapper.ToolPackageDependencyIdFactory
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyApi(
    dll: DLL<CompositeID, ToolPackageDependencyDto>
) : CompositeIdEndpointImpl<ToolPackageDependencyDto>(
    dll = dll,
    serializer = ToolPackageDependencyDto.serializer(),
    compositeIdFactory = ToolPackageDependencyIdFactory,
    validator = { _-> true }
) {

    override fun register(routing: Routing) {
        routing.apply {
            routing.apply {
                get("/api/tool-package-dep") { getAll() }
                get("/api/tool-package-dep/{id}") { getById(context.request.call) }
                post("/api/tool-package-dep") { create(context.request.call) }
                post("/api/tool-package-dep/batch") { createBatch(context.request.call) }
                put("/api/tool-package-dep") { update(context.request.call) }
                put("/api/tool-package-dep/batch") { updateBatch(context.request.call) }
                delete("/api/tool-package-dep/{id}") { delete(context.request.call) }
                delete("/api/tool-package-dep/batch") { deleteBatch(context.request.call) }
            }
        }
    }
}

