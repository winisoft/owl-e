package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.environs.PackageDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator


class PackageApi(
    dll: DLL<Long, PackageDto>,
    validator: DtoValidator<PackageDto>
): LongIdEndpointImpl<PackageDto>(
    dll = dll,
    serializer = PackageDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/package") {
                call.execute(OwleApiCall.GetMany(PackageDto::class))
            }
            get("/api/package/{id}") {
                call.execute(OwleApiCall.GetOne(PackageDto::class))
            }
            post("/api/package") {
                call.execute(OwleApiCall.CreateOne(PackageDto::class))
            }
            post("/api/package/batch") {
                call.execute(OwleApiCall.CreateMany(PackageDto::class))
            }
            put("/api/package") {
                call.execute(OwleApiCall.UpdateOne(PackageDto::class))
            }
            put("/api/package/batch") {
                call.execute(OwleApiCall.UpdateMany(PackageDto::class))
            }
            delete("/api/package/{id}") {
                call.execute(OwleApiCall.DeleteOne(PackageDto::class))
            }
            delete("/api/package/batch") {
                call.execute(OwleApiCall.DeleteMany(PackageDto::class))
            }
        }
    }

}