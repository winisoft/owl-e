package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.TransformArg
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TransformationArgApi(
    dll: DLL<Long, TransformArg>,
    validator: DtoValidator<TransformArg>
): LongIdEndpointImpl<TransformArg>(
    dll = dll,
    serializer = TransformArg.serializer(),
    validator = validator
), ITransformationArgApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation-arg") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/transformation-arg/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/transformation-arg") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/transformation-arg/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/transformation-arg") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/transformation-arg/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/transformation-arg/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/transformation-arg/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}