package com.wini.owle.api.environs

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.environs.ToolPackageDependencyDll
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.mapper.CompositeIdFactory
import com.wini.owle.mapper.ToolPackageDependencyIdFactory
import com.wini.owle.models.ToolPackageDependency
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json


class ToolPackageDependencyApi(
    toolPackageDependencyDll: ToolPackageDependencyDll
) : CompositeIdEndpointImpl<ToolPackageDependencyDto>(
    dll = toolPackageDependencyDll,
    serializer = ToolPackageDependencyDto.serializer(),
    compositeIdFactory = ToolPackageDependencyIdFactory,
    validator = { _-> true }
) {

    suspend fun getById(call: ApplicationCall): AsyncResult<ToolPackageDependencyDto> {
        return Json.decodeFromString<ToolPackageDependencyDto>(call.receiveText())
            .run { compositeIdFactory.idFromDto(this) }
            .run(dll::getById)
    }

    suspend fun delete(call: ApplicationCall): AsyncResult<Unit> {
        return Json.decodeFromString<ToolPackageDependencyDto>(call.receiveText())
            .run { compositeIdFactory.idFromDto(this) }
            .run(dll::delete)
    }

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

