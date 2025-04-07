package com.wini.owle.api.user

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.user.TagRelationDll
import com.wini.owle.dto.transforms.TransformationTargetTypeDto
import com.wini.owle.dto.user.TagRelationDto
import com.wini.owle.mapper.CompositeIdFactory
import com.wini.owle.mapper.TagRelationIdFactory
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json


class TagRelationApi(
    tagRelationDll: TagRelationDll
) : CompositeIdEndpointImpl<TagRelationDto>(
    dll = tagRelationDll,
    serializer = TagRelationDto.serializer(),
    compositeIdFactory = TagRelationIdFactory,
    validator = { _-> true }
) {

    suspend fun getById(call: ApplicationCall): AsyncResult<TagRelationDto> {
        return Json.decodeFromString<TagRelationDto>(call.receiveText())
            .run { compositeIdFactory.idFromDto(this) }
            .run(dll::getById)
    }

    suspend fun delete(call: ApplicationCall): AsyncResult<Unit> {
        return Json.decodeFromString<TagRelationDto>(call.receiveText())
            .run { compositeIdFactory.idFromDto(this) }
            .run(dll::delete)
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tag-relation") { getAll() }
            get("/api/tag-relation/{id}") { getById(context.request.call) }
            post("/api/tag-relation") { create(context.request.call) }
            post("/api/tag-relation/batch") { createBatch(context.request.call) }
            put("/api/tag-relation") { update(context.request.call) }
            put("/api/tag-relation/batch") { updateBatch(context.request.call) }
            delete("/api/tag-relation/{id}") { delete(context.request.call) }
            delete("/api/tag-relation/batch") { deleteBatch(context.request.call) }
        }
    }
}