package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.AppliedTransform
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl

class AppliedTransformApiImpl(
    dll: DLL<Long, AppliedTransform>,
    validator: DtoValidator<AppliedTransform>
): LongIdEndpointImpl<AppliedTransform>(
    dll = dll,
    serializer = AppliedTransform.serializer(),
    validator = validator
), IAppliedTransformApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/applied-transform/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/applied-transform") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/applied-transform/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/applied-transform") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/applied-transform/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/applied-transform/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/applied-transform/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}