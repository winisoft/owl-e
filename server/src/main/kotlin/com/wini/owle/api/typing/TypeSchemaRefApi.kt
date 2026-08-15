package com.wini.owle.api.typing

import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.dto.typing.TypeSchemaRef
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TypeSchemaRefApi(
    dll: DLL<Long, TypeSchemaRef>,
    validator: DtoValidator<TypeSchemaRef>
): ULongIdEndpointImpl<TypeSchemaRef>(
    dll = dll,
    serializer = TypeSchemaRef.serializer(),
    validator = validator
), ITypeSchemaRefApi {


    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-schema-ref") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/type-schema-ref/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/type-schema-ref") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/type-schema-ref/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/type-schema-ref") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/type-schema-ref/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/type-schema-ref/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/type-schema-ref/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}