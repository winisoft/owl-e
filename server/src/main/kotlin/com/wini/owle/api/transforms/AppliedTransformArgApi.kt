package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.AppliedTransformArg
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class AppliedTransformArgApi(
    dll: DLL<Long, AppliedTransformArg>,
    validator: DtoValidator<AppliedTransformArg>
): LongIdEndpointImpl<AppliedTransformArg>(
    dll = dll,
    serializer = AppliedTransformArg.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform-arg") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/applied-transform-arg/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/applied-transform-arg") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/applied-transform-arg/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/applied-transform-arg") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/applied-transform-arg/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/applied-transform-arg/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/applied-transform-arg/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}