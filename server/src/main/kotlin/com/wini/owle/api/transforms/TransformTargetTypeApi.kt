package com.wini.owle.api.transforms

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.entity.composteid.TransformTargetTypeIdFactory
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import io.ktor.util.*
import org.jetbrains.exposed.dao.id.CompositeID



class TransformTargetTypeApi(
    dll: DLL<CompositeID, TransformationTargetTypeDto>
) : CompositeIdEndpointImpl<TransformationTargetTypeDto>(
    dll = dll,
    serializer = TransformationTargetTypeDto.serializer(),
    compositeIdFactory = TransformTargetTypeIdFactory,
    validator = { _-> true }
) {

    override fun ApplicationCall.readId(): CompositeID? {
        return compositeIdFactory.fromRequestParams(params = parameters.toMap())
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transform-target-type") {
                call.asyncResultList(getAll())
            }
            get("/api/transform-target-type/id?transformationId={transformationId}&eligibleTypeDeclarationId={eligibleTypeDeclarationId}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/transform-target-type") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/transform-target-type/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/transform-target-type") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/transform-target-type/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/transform-target-type/id?transformationId={transformationId}&eligibleTypeDeclarationId={eligibleTypeDeclarationId}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/transform-target-type/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}