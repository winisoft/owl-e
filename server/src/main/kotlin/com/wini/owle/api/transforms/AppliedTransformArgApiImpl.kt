package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.AppliedTransformArg
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl

class AppliedTransformArgApiImpl(
    dll: DLL<Long, AppliedTransformArg>,
    validator: DtoValidator<AppliedTransformArg>
): LongIdEndpointImpl<AppliedTransformArg>(
    dll = dll,
    serializer = AppliedTransformArg.serializer(),
    validator = validator
), IAppliedTransformArgApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform-arg") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/applied-transform-arg/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/applied-transform-arg") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/applied-transform-arg/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/applied-transform-arg") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/applied-transform-arg/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/applied-transform-arg/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/applied-transform-arg/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}