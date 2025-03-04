package com.wini.owle.api.user

import com.wini.owle.api.GenericCompositeIdApi
import com.wini.owle.dll.user.TagRelationDll
import com.wini.owle.dto.TagRelationDto
import com.wini.owle.mapper.CompositeIdMap
import io.ktor.server.routing.*

class TagRelationApi(
    tagRelationDll: TagRelationDll,
    compositeIdMap: CompositeIdMap<TagRelationDto>
) : GenericCompositeIdApi<TagRelationDto>(
    dll = tagRelationDll,
    clazz = TagRelationDto::class,
    serializer = TagRelationDto.serializer(),
    compositeIdMapper = compositeIdMap
) {
    override fun register(routing: Routing) {
        routing.apply {
            routing.apply {
                get("/api/tag-relation") { getAll(context.request.call) }
                get("/api/tag-relation/{id}") { getById(context.request.call) }
                post("/api/tag-relation") { create(context.request.call) }
                post("/api/tag-relation/batch") { createBatch(context.request.call) }
                put("/api/tag-relation") { update(context.request.call) }
                put("/api/tag-relation/batch") { updateBatch(context.request.call) }
                delete("/api/tag-relation/{id}") { super.delete(context.request.call) }
                delete("/api/tag-relation/batch") { deleteBatch(context.request.call) }
            }
        }
    }
}