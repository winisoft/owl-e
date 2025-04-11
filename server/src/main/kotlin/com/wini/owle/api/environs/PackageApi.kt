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
                call.execute(OwleApiCall.GetMany)
            }
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