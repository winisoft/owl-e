package com.wini.owle.api.transforms

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.transforms.TransformTargetTypeDll
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.mapper.CompositeIdFactory
import com.wini.owle.mapper.TransformTargetTypeIdFactory
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import com.wini.owle.dll.DLL
import org.jetbrains.exposed.dao.id.CompositeID



class TransformTargetTypeApi(
    dll: DLL<CompositeID, TransformationTargetTypeDto>
) : CompositeIdEndpointImpl<TransformationTargetTypeDto>(
    dll = dll,
    serializer = TransformationTargetTypeDto.serializer(),
    compositeIdFactory = TransformTargetTypeIdFactory,
    validator = { _-> true }
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transform-target-type") { getAll() }
            get("/api/transform-target-type/{id}") { getById(context.request.call) }
            post("/api/transform-target-type") { create(context.request.call) }
            post("/api/transform-target-type/batch") { createBatch(context.request.call) }
            put("/api/transform-target-type") { update(context.request.call) }
            put("/api/transform-target-type/batch") { updateBatch(context.request.call) }
            delete("/api/transform-target-type/{id}") { delete(context.request.call) }
            delete("/api/transform-target-type/batch") { deleteBatch(context.request.call) }
        }
    }
}