package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.AppliedTransformDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class AppliedTransformApi(
    dll: DLL<Long, AppliedTransformDto>,
    validator: DtoValidator<AppliedTransformDto>
): LongIdEndpointImpl<AppliedTransformDto>(
    dll = dll,
    serializer = AppliedTransformDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform") {
                call.execute(OwleApiCall.GetMany(AppliedTransformDto::class))
            }
            get("/api/applied-transform/{id}") {
                call.execute(OwleApiCall.GetOne(AppliedTransformDto::class))
            }
            post("/api/applied-transform") {
                call.execute(OwleApiCall.CreateOne(AppliedTransformDto::class))
            }
            post("/api/applied-transform/batch") {
                call.execute(OwleApiCall.CreateMany(AppliedTransformDto::class))
            }
            put("/api/applied-transform") {
                call.execute(OwleApiCall.UpdateOne(AppliedTransformDto::class))
            }
            put("/api/applied-transform/batch") {
                call.execute(OwleApiCall.UpdateMany(AppliedTransformDto::class))
            }
            delete("/api/applied-transform/{id}") {
                call.execute(OwleApiCall.DeleteOne(AppliedTransformDto::class)) 
            }
            delete("/api/applied-transform/batch") {
                call.execute(OwleApiCall.DeleteMany(AppliedTransformDto::class))
            }
        }
    }
}