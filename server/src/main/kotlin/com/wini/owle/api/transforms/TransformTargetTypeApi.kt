package com.wini.owle.api.transforms

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.validators.DtoValidator
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.id.CompositeID
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall


class TransformTargetTypeApi(
    dll: DLL<CompositeID, TransformTargetType>,
    validator: DtoValidator<TransformTargetType>,
    compositeIdFactory: CompositeIdFactory<TransformTargetType>
): CompositeIdEndpointImpl<TransformTargetType>(
    dll = dll,
    serializer = TransformTargetType.serializer(),
    compositeIdFactory = compositeIdFactory,
    validator = validator
), ITransformTargetTypeApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transform-target-type") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/transform-target-type/id") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/transform-target-type") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/transform-target-type/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/transform-target-type") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/transform-target-type/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/transform-target-type/id") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/transform-target-type/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}