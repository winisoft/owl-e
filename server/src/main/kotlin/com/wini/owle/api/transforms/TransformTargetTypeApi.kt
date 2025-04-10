package com.wini.owle.api.transforms

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.entity.composteid.TransformTargetTypeIdFactory
import com.wini.owle.validators.DtoValidator
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.id.CompositeID


class TransformTargetTypeApi(
    dll: DLL<CompositeID, TransformationTargetTypeDto>,
    validator: DtoValidator<TransformationTargetTypeDto>
): CompositeIdEndpointImpl<TransformationTargetTypeDto>(
    dll = dll,
    serializer = TransformationTargetTypeDto.serializer(),
    compositeIdFactory = TransformTargetTypeIdFactory,
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transform-target-type") {
                call.execute(OwleApiCall.GetMany(TransformationTargetTypeDto::class))
            }
            get("/api/transform-target-type/id?transformationId={transformationId}&eligibleTypeDeclarationId={eligibleTypeDeclarationId}") {
                call.execute(OwleApiCall.GetOne(TransformationTargetTypeDto::class))
            }
            post("/api/transform-target-type") {
                call.execute(OwleApiCall.CreateOne(TransformationTargetTypeDto::class))
            }
            post("/api/transform-target-type/batch") {
                call.execute(OwleApiCall.CreateMany(TransformationTargetTypeDto::class))
            }
            put("/api/transform-target-type") {
                call.execute(OwleApiCall.UpdateOne(TransformationTargetTypeDto::class))
            }
            put("/api/transform-target-type/batch") {
                call.execute(OwleApiCall.UpdateMany(TransformationTargetTypeDto::class))
            }
            delete("/api/transform-target-type/id?transformationId={transformationId}&eligibleTypeDeclarationId={eligibleTypeDeclarationId}") {
                call.execute(OwleApiCall.DeleteOne(TransformationTargetTypeDto::class)) 
            }
            delete("/api/transform-target-type/batch") {
                call.execute(OwleApiCall.DeleteMany(TransformationTargetTypeDto::class))
            }
        }
    }
}