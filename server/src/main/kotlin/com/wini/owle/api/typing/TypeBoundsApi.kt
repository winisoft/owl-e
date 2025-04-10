package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeBoundsDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TypeBoundsApi(
    dll: DLL<Long, TypeBoundsDto>,
    validator: DtoValidator<TypeBoundsDto>
): LongIdEndpointImpl<TypeBoundsDto>(
    dll = dll,
    serializer = TypeBoundsDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-bounds") {
                call.execute(OwleApiCall.GetMany(TypeBoundsDto::class))
            }
            get("/api/type-bounds/{id}") {
                call.execute(OwleApiCall.GetOne(TypeBoundsDto::class))
            }
            post("/api/type-bounds") {
                call.execute(OwleApiCall.CreateOne(TypeBoundsDto::class))
            }
            post("/api/type-bounds/batch") {
                call.execute(OwleApiCall.CreateMany(TypeBoundsDto::class))
            }
            put("/api/type-bounds") {
                call.execute(OwleApiCall.UpdateOne(TypeBoundsDto::class))
            }
            put("/api/type-bounds/batch") {
                call.execute(OwleApiCall.UpdateMany(TypeBoundsDto::class))
            }
            delete("/api/type-bounds/{id}") {
                call.execute(OwleApiCall.DeleteOne(TypeBoundsDto::class)) 
            }
            delete("/api/type-bounds/batch") {
                call.execute(OwleApiCall.DeleteMany(TypeBoundsDto::class))
            }
        }
    }
}