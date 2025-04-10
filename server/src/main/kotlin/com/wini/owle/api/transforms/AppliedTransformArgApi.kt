package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.AppliedTransformArgDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class AppliedTransformArgApi(
    dll: DLL<Long, AppliedTransformArgDto>,
    validator: DtoValidator<AppliedTransformArgDto>
): LongIdEndpointImpl<AppliedTransformArgDto>(
    dll = dll,
    serializer = AppliedTransformArgDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform-arg") {
                call.execute(OwleApiCall.GetMany(AppliedTransformArgDto::class))
            }
            get("/api/applied-transform-arg/{id}") {
                call.execute(OwleApiCall.GetOne(AppliedTransformArgDto::class))
            }
            post("/api/applied-transform-arg") {
                call.execute(OwleApiCall.CreateOne(AppliedTransformArgDto::class))
            }
            post("/api/applied-transform-arg/batch") {
                call.execute(OwleApiCall.CreateMany(AppliedTransformArgDto::class))
            }
            put("/api/applied-transform-arg") {
                call.execute(OwleApiCall.UpdateOne(AppliedTransformArgDto::class))
            }
            put("/api/applied-transform-arg/batch") {
                call.execute(OwleApiCall.UpdateMany(AppliedTransformArgDto::class))
            }
            delete("/api/applied-transform-arg/{id}") {
                call.execute(OwleApiCall.DeleteOne(AppliedTransformArgDto::class)) 
            }
            delete("/api/applied-transform-arg/batch") {
                call.execute(OwleApiCall.DeleteMany(AppliedTransformArgDto::class))
            }
        }
    }
}