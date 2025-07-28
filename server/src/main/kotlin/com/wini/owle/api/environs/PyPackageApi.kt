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

class PyPackageApi(
    dll: DLL<Long, PyPackage>,
    validator: DtoValidator<PyPackage>
): LongIdEndpointImpl<PyPackage>(
    dll = dll,
    serializer = PyPackage.serializer(),
    validator = validator
), IPyPackageApi  {

    override fun register(routing: Routing) {
        routing.apply {

            get("/api/package") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }

            get("/api/package/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }

            post("/api/package") {
                postMany { postMany { call.execute(OwleApiCall.CreateOne) } }
            }
            post("/api/package/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/package") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/package/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/package/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) }
            }
            delete("/api/package/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }

}