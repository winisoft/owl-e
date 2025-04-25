package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeVariance
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TypeVarianceApi(
    dll: DLL<Long, TypeVariance>,
    validator: DtoValidator<TypeVariance>
): LongIdEndpointImpl<TypeVariance>(
    dll = dll,
    serializer = TypeVariance.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-variance") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/type-variance/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/type-variance") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/type-variance/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/type-variance") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/type-variance/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/type-variance/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/type-variance/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}