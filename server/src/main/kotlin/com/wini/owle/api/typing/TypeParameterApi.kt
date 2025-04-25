package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeParameter
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TypeParameterApi(
    dll: DLL<Long, TypeParameter>,
    validator: DtoValidator<TypeParameter>
): LongIdEndpointImpl<TypeParameter>(
    dll = dll,
    serializer = TypeParameter.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-parameter") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/type-parameter/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/type-parameter") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/type-parameter/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/type-parameter") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/type-parameter/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/type-parameter/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/type-parameter/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}