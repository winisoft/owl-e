package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeVarianceDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TypeVarianceApi(
    dll: DLL<Long, TypeVarianceDto>,
    validator: DtoValidator<TypeVarianceDto>
): LongIdEndpointImpl<TypeVarianceDto>(
    dll = dll,
    serializer = TypeVarianceDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-variance") {
                call.execute(OwleApiCall.GetMany(TypeVarianceDto::class))
            }
            get("/api/type-variance/{id}") {
                call.execute(OwleApiCall.GetOne(TypeVarianceDto::class))
            }
            post("/api/type-variance") {
                call.execute(OwleApiCall.CreateOne(TypeVarianceDto::class))
            }
            post("/api/type-variance/batch") {
                call.execute(OwleApiCall.CreateMany(TypeVarianceDto::class))
            }
            put("/api/type-variance") {
                call.execute(OwleApiCall.UpdateOne(TypeVarianceDto::class))
            }
            put("/api/type-variance/batch") {
                call.execute(OwleApiCall.UpdateMany(TypeVarianceDto::class))
            }
            delete("/api/type-variance/{id}") {
                call.execute(OwleApiCall.DeleteOne(TypeVarianceDto::class)) 
            }
            delete("/api/type-variance/batch") {
                call.execute(OwleApiCall.DeleteMany(TypeVarianceDto::class))
            }
        }
    }
}