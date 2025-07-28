package com.wini.owle.api.environs

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.ToolPyPackDependency
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.validators.DtoValidator
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.id.CompositeID


class ToolPyPackDepApi(
    dll: DLL<CompositeID, ToolPyPackDependency>,
    validator: DtoValidator<ToolPyPackDependency>,
    compositeIdFactory: CompositeIdFactory<ToolPyPackDependency>
) : CompositeIdEndpointImpl<ToolPyPackDependency>(
    dll = dll,
    serializer = ToolPyPackDependency.serializer(),
    compositeIdFactory = compositeIdFactory,
    validator = validator
), IToolPyPackDepApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-package-dep") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/tool-package-dep/id") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/tool-package-dep") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/tool-package-dep/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/tool-package-dep") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/tool-package-dep/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/tool-package-dep/id") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) }
            }
            delete("/api/tool-package-dep/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}

