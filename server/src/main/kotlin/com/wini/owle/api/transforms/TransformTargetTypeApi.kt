package com.wini.owle.api.transforms

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.TransformTargetTypeIdFactory
import com.wini.owle.validators.DtoValidator
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.id.CompositeID


class TransformTargetTypeApi(
    dll: DLL<CompositeID, TransformationTargetTypeDto>,
    validator: DtoValidator<TransformationTargetTypeDto>,
    compositeIdFactory: CompositeIdFactory<TransformationTargetTypeDto>
): CompositeIdEndpointImpl<TransformationTargetTypeDto>(
    dll = dll,
    serializer = TransformationTargetTypeDto.serializer(),
    compositeIdFactory = compositeIdFactory,
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transform-target-type") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/transform-target-type/id") {
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
            delete("/api/transform-target-type/id") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/transform-target-type/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}