package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeDeclaration
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TypeDeclarationApi(
    dll: DLL<Long, TypeDeclaration>,
    validator: DtoValidator<TypeDeclaration>
): LongIdEndpointImpl<TypeDeclaration>(
    dll = dll,
    serializer = TypeDeclaration.serializer(),
    validator = validator
), ITypeDeclarationApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-declaration") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/type-declaration/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/type-declaration") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/type-declaration/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/type-declaration") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/type-declaration/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/type-declaration/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/type-declaration/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}