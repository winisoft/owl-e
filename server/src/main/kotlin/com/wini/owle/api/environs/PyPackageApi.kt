package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PyPackage
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator
import com.wini.owle.content.ApiMethod
import com.wini.owle.content.ApiMethodSuccessCase
import com.wini.owle.content.OwleApiEndpoint


@OwleApiEndpoint(
    "Manages PyPackages installed into runtime environments",
    tags = [ "environment", "container", "runtime" ]
)
class PyPackageApi(
    dll: DLL<Long, PyPackage>,
    validator: DtoValidator<PyPackage>
): LongIdEndpointImpl<PyPackage>(
    dll = dll,
    serializer = PyPackage.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {

            @ApiMethod(
                summary = "Get all PyPackages",
                description = "Gets a list of all PyPackages configured into workflow run environments.")
            @ApiMethodSuccessCase(
                json = PyPackage.JSON_LIST_EXAMPLE)
            get("/api/package") {
                call.execute(OwleApiCall.GetMany)
            }

            @ApiMethod(
                summary = "Get a PyPackage by ID",
                description = "Gets a list of all PyPackages configured into workflow run environments.")
            @ApiMethodSuccessCase(
                json = PyPackage.JSON_ONE_EXAMPLE)
            get("/api/package/{id}") {
                call.execute(OwleApiCall.GetOne)
            }

            post("/api/package") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/package/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/package") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/package/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/package/{id}") {
                call.execute(OwleApiCall.DeleteOne)
            }
            delete("/api/package/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }

}