package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeBound
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TypeBoundsApi(
    dll: DLL<Long, TypeBound>,
    validator: DtoValidator<TypeBound>
): LongIdEndpointImpl<TypeBound>(
    dll = dll,
    serializer = TypeBound.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-bounds") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/type-bounds/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/type-bounds") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/type-bounds/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/type-bounds") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/type-bounds/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/type-bounds/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/type-bounds/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}