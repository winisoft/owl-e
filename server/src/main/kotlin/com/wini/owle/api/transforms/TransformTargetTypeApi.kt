package com.wini.owle.api.transforms

import com.wini.owle.api.GenericCompositeIdApi
import com.wini.owle.dll.transforms.TransformTargetTypeDll
import com.wini.owle.dto.TransformationTargetTypeDto
import com.wini.owle.mapper.CompositeIdMap
import io.ktor.server.routing.*

class TransformTargetTypeApi(
    transformTargetTypeDll: TransformTargetTypeDll,
    compositeIdMap: CompositeIdMap<TransformationTargetTypeDto>
) : GenericCompositeIdApi<TransformationTargetTypeDto>(
    dll = transformTargetTypeDll,
    clazz = TransformationTargetTypeDto::class,
    serializer = TransformationTargetTypeDto.serializer(),
    compositeIdMapper = compositeIdMap
) {
    override fun register(routing: Routing) {
        routing.apply {
            routing.apply {
                get("/api/transform-target-type") { getAll(context.request.call) }
                get("/api/transform-target-type/{id}") { getById(context.request.call) }
                post("/api/transform-target-type") { create(context.request.call) }
                post("/api/transform-target-type/batch") { createBatch(context.request.call) }
                put("/api/transform-target-type") { update(context.request.call) }
                put("/api/transform-target-type/batch") { updateBatch(context.request.call) }
                delete("/api/transform-target-type/{id}") { super.delete(context.request.call) }
                delete("/api/transform-target-type/batch") { deleteBatch(context.request.call) }
            }
        }
    }
}