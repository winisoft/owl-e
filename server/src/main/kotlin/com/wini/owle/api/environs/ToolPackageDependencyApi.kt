package com.wini.owle.api.environs

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.entity.composteid.ToolPackageDepIdFactory
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyApi(
    dll: DLL<CompositeID, ToolPackageDependencyDto>,
    validator: (ToolPackageDependencyDto) -> Boolean = { _-> true }
) : CompositeIdEndpointImpl<ToolPackageDependencyDto>(
    dll = dll,
    serializer = ToolPackageDependencyDto.serializer(),
    compositeIdFactory = ToolPackageDepIdFactory,
    validator = validator
) {
    override fun ApplicationCall.readId(): CompositeID? {
        return compositeIdFactory.fromRequestParams(params = parameters.toMap())
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-package-dep") {
                call.asyncResultList(getAll())
            }
            get("/api/tool-package-dep/id?toolId={toolId}&packageId={packageId}&versionSpecId={versionSpecId}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/tool-package-dep") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/tool-package-dep/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/tool-package-dep") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/tool-package-dep/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/tool-package-dep/tool-package-dep/id?toolId={toolId}&packageId={packageId}&versionSpecId={versionSpecId}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/tool-package-dep/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}

