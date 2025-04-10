package com.wini.owle.api.environs

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.OwleApiCall
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.entity.composteid.ToolPackageDepIdFactory
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyApi(
    dll: DLL<CompositeID, ToolPackageDependencyDto>,
    validator: DtoValidator<ToolPackageDependencyDto>
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
                call.execute(OwleApiCall.GetMany(PackageRepoDto::class))
            }
            get("/api/tool-package-dep/id?toolId={toolId}&packageId={packageId}&versionSpecId={versionSpecId}") {
                call.execute(OwleApiCall.GetOne(PackageRepoDto::class))
            }
            post("/api/tool-package-dep") {
                call.execute(OwleApiCall.CreateOne(PackageRepoDto::class))
            }
            post("/api/tool-package-dep/batch") {
                call.execute(OwleApiCall.CreateMany(PackageRepoDto::class))
            }
            put("/api/tool-package-dep") {
                call.execute(OwleApiCall.UpdateOne(PackageRepoDto::class))
            }
            put("/api/tool-package-dep/batch") {
                call.execute(OwleApiCall.UpdateMany(PackageRepoDto::class))
            }
            delete("/api/tool-package-dep/tool-package-dep/id?toolId={toolId}&packageId={packageId}&versionSpecId={versionSpecId}") {
                call.execute(OwleApiCall.DeleteOne(PackageRepoDto::class))
            }
            delete("/api/tool-package-dep/batch") {
                call.execute(OwleApiCall.DeleteMany(PackageRepoDto::class))
            }
        }
    }
}

