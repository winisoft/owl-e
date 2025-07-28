package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeBound
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TypeBoundsApi(
    dll: DLL<Long, TypeBound>,
    validator: DtoValidator<TypeBound>
): LongIdEndpointImpl<TypeBound>(
    dll = dll,
    serializer = TypeBound.serializer(),
    validator = validator
), ITypeBoundsApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-bounds") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/type-bounds/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/type-bounds") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/type-bounds/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/type-bounds") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/type-bounds/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/type-bounds/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/type-bounds/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}