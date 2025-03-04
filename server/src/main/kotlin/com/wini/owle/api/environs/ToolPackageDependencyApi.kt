package com.wini.owle.api.environs

import com.wini.owle.api.GenericCompositeIdApi
import com.wini.owle.dll.environs.ToolPackageDependencyDll
import com.wini.owle.dto.*
import com.wini.owle.mapper.CompositeIdMap
import io.ktor.server.routing.*


class ToolPackageDependencyApi(
    toolPackageDependencyDll: ToolPackageDependencyDll,
    compositeIdMap: CompositeIdMap<ToolPackageDependencyDto>
) : GenericCompositeIdApi<ToolPackageDependencyDto>(
    dll = toolPackageDependencyDll,
    clazz = ToolPackageDependencyDto::class,
    serializer = ToolPackageDependencyDto.serializer(),
    compositeIdMapper = compositeIdMap
) {
    override fun register(routing: Routing) {
        routing.apply {
            routing.apply {
                get("/api/tool-package-dep") { getAll(context.request.call) }
                get("/api/tool-package-dep/{id}") { getById(context.request.call) }
                post("/api/tool-package-dep") { create(context.request.call) }
                post("/api/tool-package-dep/batch") { createBatch(context.request.call) }
                put("/api/tool-package-dep") { update(context.request.call) }
                put("/api/tool-package-dep/batch") { updateBatch(context.request.call) }
                delete("/api/tool-package-dep/{id}") { super.delete(context.request.call) }
                delete("/api/tool-package-dep/batch") { deleteBatch(context.request.call) }
            }
        }
    }
}

