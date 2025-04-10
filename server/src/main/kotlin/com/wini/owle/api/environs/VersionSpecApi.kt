package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.environs.VersionSpecDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.validators.DtoValidator


class VersionSpecApi(
    dll: DLL<Long, VersionSpecDto>,
    validator: DtoValidator<VersionSpecDto>
) : LongIdEndpointImpl<VersionSpecDto>(
    dll = dll,
    serializer = VersionSpecDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/version-spec") {
                call.execute(OwleApiCall.GetMany(PackageRepoDto::class))
            }
            get("/api/version-spec/{id}") {
                call.execute(OwleApiCall.GetOne(PackageRepoDto::class))
            }
            post("/api/version-spec") {
                call.execute(OwleApiCall.CreateOne(PackageRepoDto::class))
            }
            post("/api/version-spec/batch") {
                call.execute(OwleApiCall.CreateMany(PackageRepoDto::class))
            }
            put("/api/version-spec") {
                call.execute(OwleApiCall.UpdateOne(PackageRepoDto::class))
            }
            put("/api/version-spec/batch") {
                call.execute(OwleApiCall.UpdateMany(PackageRepoDto::class))
            }
            delete("/api/version-spec/{id}") {
                call.execute(OwleApiCall.DeleteOne(PackageRepoDto::class))
            }
            delete("/api/version-spec/batch") {
                call.execute(OwleApiCall.DeleteMany(PackageRepoDto::class))
            }
        }
    }
}