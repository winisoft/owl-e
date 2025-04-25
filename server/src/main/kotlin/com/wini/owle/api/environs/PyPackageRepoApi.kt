package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PyPackageRepo
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class PyPackageRepoApi(
    dll: DLL<Long, PyPackageRepo>,
    validator: DtoValidator<PyPackageRepo>
): LongIdEndpointImpl<PyPackageRepo>(
    dll = dll,
    serializer = PyPackageRepo.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/package-repo") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/package-repo/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/package-repo") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/package-repo/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/package-repo") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/package-repo/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/package-repo/{id}") {
                call.execute(OwleApiCall.DeleteOne)
            }
            delete("/api/package-repo/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}