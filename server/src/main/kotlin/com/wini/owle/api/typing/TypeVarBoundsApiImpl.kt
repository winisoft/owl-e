package com.wini.owle.api.typing

import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dll.DLL
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.entity.typing.TypeVarBounds

import com.wini.owle.validators.DtoValidator

class TypeVarBoundsApiImpl(
    dll: DLL<ULong, TypeVarBound>,
    validator: DtoValidator<TypeVarBound>,
    override val endpointPath: String
): ULongIdEndpointImpl<TypeVarBound>(
    dll = dll,
    serializer = TypeVarBound.serializer(),
    endpointPath = endpointPath,
    validator = validator
), TypeVarBoundApi {

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