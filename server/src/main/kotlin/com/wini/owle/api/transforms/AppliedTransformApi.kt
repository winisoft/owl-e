package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.AppliedTransform
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class AppliedTransformApi(
    dll: DLL<Long, AppliedTransform>,
    validator: DtoValidator<AppliedTransform>
): LongIdEndpointImpl<AppliedTransform>(
    dll = dll,
    serializer = AppliedTransform.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/applied-transform/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/applied-transform") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/applied-transform/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/applied-transform") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/applied-transform/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/applied-transform/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/applied-transform/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}