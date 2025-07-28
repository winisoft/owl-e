package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeVariance
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TypeVarianceApi(
    dll: DLL<Long, TypeVariance>,
    validator: DtoValidator<TypeVariance>
): LongIdEndpointImpl<TypeVariance>(
    dll = dll,
    serializer = TypeVariance.serializer(),
    validator = validator
), ITypeVarianceApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-variance") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/type-variance/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/type-variance") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/type-variance/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/type-variance") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/type-variance/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/type-variance/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/type-variance/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}