package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeParameterDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TypeParameterApi(
    dll: DLL<Long, TypeParameterDto>,
    validator: DtoValidator<TypeParameterDto>
): LongIdEndpointImpl<TypeParameterDto>(
    dll = dll,
    serializer = TypeParameterDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-parameter") {
                call.execute(OwleApiCall.GetMany(TypeParameterDto::class))
            }
            get("/api/type-parameter/{id}") {
                call.execute(OwleApiCall.GetOne(TypeParameterDto::class))
            }
            post("/api/type-parameter") {
                call.execute(OwleApiCall.CreateOne(TypeParameterDto::class))
            }
            post("/api/type-parameter/batch") {
                call.execute(OwleApiCall.CreateMany(TypeParameterDto::class))
            }
            put("/api/type-parameter") {
                call.execute(OwleApiCall.UpdateOne(TypeParameterDto::class))
            }
            put("/api/type-parameter/batch") {
                call.execute(OwleApiCall.UpdateMany(TypeParameterDto::class))
            }
            delete("/api/type-parameter/{id}") {
                call.execute(OwleApiCall.DeleteOne(TypeParameterDto::class)) 
            }
            delete("/api/type-parameter/batch") {
                call.execute(OwleApiCall.DeleteMany(TypeParameterDto::class))
            }
        }
    }
}