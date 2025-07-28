package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dto.environs.VersionSpec
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator


class VersionSpecApi(
    dll: DLL<Long, VersionSpec>,
    validator: DtoValidator<VersionSpec>
) : LongIdEndpointImpl<VersionSpec>(
    dll = dll,
    serializer = VersionSpec.serializer(),
    validator = validator
), IVersionSpecApi  {

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