package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeDefinition
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TypeDefinitionApi(
    dll: DLL<Long, TypeDefinition>,
    validator: DtoValidator<TypeDefinition>
): LongIdEndpointImpl<TypeDefinition>(
    dll = dll,
    serializer = TypeDefinition.serializer(),
    validator = validator
), ITypeDefinitionApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-definition") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/type-definition/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/type-definition") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/type-definition/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/type-definition") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/type-definition/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/type-definition/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/type-definition/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}