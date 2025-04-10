package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.TransformationArgDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TransformationArgApi(
    dll: DLL<Long, TransformationArgDto>,
    validator: DtoValidator<TransformationArgDto>
): LongIdEndpointImpl<TransformationArgDto>(
    dll = dll,
    serializer = TransformationArgDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation-arg") {
                call.execute(OwleApiCall.GetMany(TransformationArgDto::class))
            }
            get("/api/transformation-arg/{id}") {
                call.execute(OwleApiCall.GetOne(TransformationArgDto::class))
            }
            post("/api/transformation-arg") {
                call.execute(OwleApiCall.CreateOne(TransformationArgDto::class))
            }
            post("/api/transformation-arg/batch") {
                call.execute(OwleApiCall.CreateMany(TransformationArgDto::class))
            }
            put("/api/transformation-arg") {
                call.execute(OwleApiCall.UpdateOne(TransformationArgDto::class))
            }
            put("/api/transformation-arg/batch") {
                call.execute(OwleApiCall.UpdateMany(TransformationArgDto::class))
            }
            delete("/api/transformation-arg/{id}") {
                call.execute(OwleApiCall.DeleteOne(TransformationArgDto::class)) 
            }
            delete("/api/transformation-arg/batch") {
                call.execute(OwleApiCall.DeleteMany(TransformationArgDto::class))
            }
        }
    }
}