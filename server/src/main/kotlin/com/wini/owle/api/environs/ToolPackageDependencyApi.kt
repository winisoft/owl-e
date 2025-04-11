package com.wini.owle.api.environs

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.ToolPackageDepIdFactory
import com.wini.owle.validators.DtoValidator
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPackageDependencyApi(
    dll: DLL<CompositeID, ToolPackageDependencyDto>,
    validator: DtoValidator<ToolPackageDependencyDto>,
    compositeIdFactory: CompositeIdFactory<ToolPackageDependencyDto>
) : CompositeIdEndpointImpl<ToolPackageDependencyDto>(
    dll = dll,
    serializer = ToolPackageDependencyDto.serializer(),
    compositeIdFactory = compositeIdFactory,
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-package-dep") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/tool-package-dep/id") {
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
            delete("/api/tool-package-dep/id") {
                call.execute(OwleApiCall.DeleteOne)
            }
            delete("/api/tool-package-dep/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}

