package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeDeclarationDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TypeDeclarationApi(
    dll: DLL<Long, TypeDeclarationDto>,
    validator: DtoValidator<TypeDeclarationDto>
): LongIdEndpointImpl<TypeDeclarationDto>(
    dll = dll,
    serializer = TypeDeclarationDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-declaration") {
                call.execute(OwleApiCall.GetMany(TypeDeclarationDto::class))
            }
            get("/api/type-declaration/{id}") {
                call.execute(OwleApiCall.GetOne(TypeDeclarationDto::class))
            }
            post("/api/type-declaration") {
                call.execute(OwleApiCall.CreateOne(TypeDeclarationDto::class))
            }
            post("/api/type-declaration/batch") {
                call.execute(OwleApiCall.CreateMany(TypeDeclarationDto::class))
            }
            put("/api/type-declaration") {
                call.execute(OwleApiCall.UpdateOne(TypeDeclarationDto::class))
            }
            put("/api/type-declaration/batch") {
                call.execute(OwleApiCall.UpdateMany(TypeDeclarationDto::class))
            }
            delete("/api/type-declaration/{id}") {
                call.execute(OwleApiCall.DeleteOne(TypeDeclarationDto::class)) 
            }
            delete("/api/type-declaration/batch") {
                call.execute(OwleApiCall.DeleteMany(TypeDeclarationDto::class))
            }
        }
    }
}