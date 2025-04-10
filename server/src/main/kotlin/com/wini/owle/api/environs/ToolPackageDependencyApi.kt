package com.wini.owle.api.environs

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.entity.composteid.ToolPackageDepIdFactory
import com.wini.owle.ifNullExit
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

    override fun ApplicationCall.readId(): AsyncResult<CompositeID> {
        return compositeIdFactory.fromRequestParams(params = parameters.toMap())
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-package-dep") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/tool-package-dep/id?toolId={toolId}&packageId={packageId}&versionSpecId={versionSpecId}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/tool-package-dep") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/tool-package-dep/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/tool-package-dep") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/tool-package-dep/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/tool-package-dep/tool-package-dep/id?toolId={toolId}&packageId={packageId}&versionSpecId={versionSpecId}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/tool-package-dep/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}

