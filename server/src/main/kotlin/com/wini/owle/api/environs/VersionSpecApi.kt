package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.environs.VersionSpecDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall


class VersionSpecApi(
    dll: DLL<Long, VersionSpecDto>,
    validator: (VersionSpecDto) -> Boolean = { _-> true}
) : LongIdEndpointImpl<VersionSpecDto>(
    dll = dll,
    serializer = VersionSpecDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/version-spec") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/version-spec/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/version-spec") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/version-spec/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/version-spec") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/version-spec/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/version-spec/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/version-spec/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}