package com.wini.owle.api.transforms

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.entity.composteid.TransformTargetTypeIdFactory
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import io.ktor.util.*
import org.jetbrains.exposed.dao.id.CompositeID
import com.wini.owle.api.OwleApiCall
import com.wini.owle.async.AsyncResult
import com.wini.owle.validators.DtoValidator


class TransformTargetTypeApi(
    dll: DLL<CompositeID, TransformationTargetTypeDto>,
    validator: DtoValidator<TransformationTargetTypeDto>
): CompositeIdEndpointImpl<TransformationTargetTypeDto>(
    dll = dll,
    serializer = TransformationTargetTypeDto.serializer(),
    compositeIdFactory = TransformTargetTypeIdFactory,
    validator = validator
) {

    override fun ApplicationCall.readId(): AsyncResult<CompositeID> {
        return compositeIdFactory.fromRequestParams(params = parameters.toMap())
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transform-target-type") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/transform-target-type/id?transformationId={transformationId}&eligibleTypeDeclarationId={eligibleTypeDeclarationId}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/transform-target-type") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/transform-target-type/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/transform-target-type") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/transform-target-type/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/transform-target-type/id?transformationId={transformationId}&eligibleTypeDeclarationId={eligibleTypeDeclarationId}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/transform-target-type/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}