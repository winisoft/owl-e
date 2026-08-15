package com.wini.owle.api.environs


import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dto.environs.pkg.PackageDistRequirement
import com.wini.owle.validators.DtoValidator


class PackageDistRequirementsApiImpl(
    dll: DLL<ULong, PackageDistRequirement>,
    validator: DtoValidator<PackageDistRequirement>,
    override val endpointPath: String
) : ULongIdEndpointImpl<PackageDistRequirement>(
    dll = dll,
    serializer = PackageDistRequirement.serializer(),
    endpointPath = endpointPath,
    validator = validator
), PackageDistRequirements {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/version-spec") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/version-spec/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/version-spec") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/version-spec/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/version-spec") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/version-spec/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/version-spec/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) }
            }
            delete("/api/version-spec/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}