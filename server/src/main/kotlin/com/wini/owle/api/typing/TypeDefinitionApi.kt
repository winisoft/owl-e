package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeDefinitionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TypeDefinitionApi(
    dll: DLL<Long, TypeDefinitionDto>,
    validator: DtoValidator<TypeDefinitionDto>
): LongIdEndpointImpl<TypeDefinitionDto>(
    dll = dll,
    serializer = TypeDefinitionDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-definition") {
                call.execute(OwleApiCall.GetMany(TypeDefinitionDto::class))
            }
            get("/api/type-definition/{id}") {
                call.execute(OwleApiCall.GetOne(TypeDefinitionDto::class))
            }
            post("/api/type-definition") {
                call.execute(OwleApiCall.CreateOne(TypeDefinitionDto::class))
            }
            post("/api/type-definition/batch") {
                call.execute(OwleApiCall.CreateMany(TypeDefinitionDto::class))
            }
            put("/api/type-definition") {
                call.execute(OwleApiCall.UpdateOne(TypeDefinitionDto::class))
            }
            put("/api/type-definition/batch") {
                call.execute(OwleApiCall.UpdateMany(TypeDefinitionDto::class))
            }
            delete("/api/type-definition/{id}") {
                call.execute(OwleApiCall.DeleteOne(TypeDefinitionDto::class)) 
            }
            delete("/api/type-definition/batch") {
                call.execute(OwleApiCall.DeleteMany(TypeDefinitionDto::class))
            }
        }
    }
}