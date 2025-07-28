package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.transforms.ITransformationApi
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.validators.DtoValidator
import io.ktor.server.application.*
import io.ktor.server.routing.*


class TransformationApi(
    dll: DLL<Long, Transform>,
    validator: DtoValidator<Transform>
): LongIdEndpointImpl<Transform>(
    dll = dll,
    serializer = Transform.serializer(),
    validator = validator
), ITransformationApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/transformation/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/transformation") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/transformation/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/transformation") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/transformation/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/transformation/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/transformation/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}