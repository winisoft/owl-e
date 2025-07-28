package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeParameter
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TypeParameterApi(
    dll: DLL<Long, TypeParameter>,
    validator: DtoValidator<TypeParameter>
): LongIdEndpointImpl<TypeParameter>(
    dll = dll,
    serializer = TypeParameter.serializer(),
    validator = validator
), ITypeParameterApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-parameter") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/type-parameter/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/type-parameter") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/type-parameter/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/type-parameter") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/type-parameter/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/type-parameter/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/type-parameter/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}