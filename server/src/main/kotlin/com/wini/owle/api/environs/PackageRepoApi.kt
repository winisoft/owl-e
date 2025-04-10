package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PackageRepoDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class PackageRepoApi(
    dll: DLL<Long, PackageRepoDto>,
    validator: DtoValidator<PackageRepoDto>
): LongIdEndpointImpl<PackageRepoDto>(
    dll = dll,
    serializer = PackageRepoDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/package-repo") {
                call.execute(OwleApiCall.GetMany(PackageRepoDto::class))
            }
            get("/api/package-repo/{id}") {
                call.execute(OwleApiCall.GetOne(PackageRepoDto::class))
            }
            post("/api/package-repo") {
                call.execute(OwleApiCall.CreateOne(PackageRepoDto::class))
            }
            post("/api/package-repo/batch") {
                call.execute(OwleApiCall.CreateMany(PackageRepoDto::class))
            }
            put("/api/package-repo") {
                call.execute(OwleApiCall.UpdateOne(PackageRepoDto::class))
            }
            put("/api/package-repo/batch") {
                call.execute(OwleApiCall.UpdateMany(PackageRepoDto::class))
            }
            delete("/api/package-repo/{id}") {
                call.execute(OwleApiCall.DeleteOne(PackageRepoDto::class))
            }
            delete("/api/package-repo/batch") {
                call.execute(OwleApiCall.DeleteMany(PackageRepoDto::class))
            }
        }
    }
}