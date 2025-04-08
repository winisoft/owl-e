package com.wini.owle.api.environs

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.entity.composteid.ToolPackageDepIdFactory
import com.wini.owle.entity.composteid.TransformTargetTypeIdFactory
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyApi(
    dll: DLL<CompositeID, ToolPackageDependencyDto>
) : CompositeIdEndpointImpl<ToolPackageDependencyDto>(
    dll = dll,
    serializer = ToolPackageDependencyDto.serializer(),
    compositeIdFactory = ToolPackageDepIdFactory,
    validator = { _-> true }
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
                call.asyncResultSingle(getById(call))
            }
            post("/api/tool-package-dep") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/tool-package-dep/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/tool-package-dep") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/tool-package-dep/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/tool-package-dep/tool-package-dep/id?toolId={toolId}&packageId={packageId}&versionSpecId={versionSpecId}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/tool-package-dep/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}

