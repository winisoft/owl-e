package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeSchemaRefDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TypeSchemaRefApi(
    dll: DLL<Long, TypeSchemaRefDto>,
    validator: DtoValidator<TypeSchemaRefDto>
): LongIdEndpointImpl<TypeSchemaRefDto>(
    dll = dll,
    serializer = TypeSchemaRefDto.serializer(),
    validator = validator
) {


    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-schema-ref") {
                call.execute(OwleApiCall.GetMany(TypeSchemaRefDto::class))
            }
            get("/api/type-schema-ref/{id}") {
                call.execute(OwleApiCall.GetOne(TypeSchemaRefDto::class))
            }
            post("/api/type-schema-ref") {
                call.execute(OwleApiCall.CreateOne(TypeSchemaRefDto::class))
            }
            post("/api/type-schema-ref/batch") {
                call.execute(OwleApiCall.CreateMany(TypeSchemaRefDto::class))
            }
            put("/api/type-schema-ref") {
                call.execute(OwleApiCall.UpdateOne(TypeSchemaRefDto::class))
            }
            put("/api/type-schema-ref/batch") {
                call.execute(OwleApiCall.UpdateMany(TypeSchemaRefDto::class))
            }
            delete("/api/type-schema-ref/{id}") {
                call.execute(OwleApiCall.DeleteOne(TypeSchemaRefDto::class)) 
            }
            delete("/api/type-schema-ref/batch") {
                call.execute(OwleApiCall.DeleteMany(TypeSchemaRefDto::class))
            }
        }
    }
}