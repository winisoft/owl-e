package com.wini.owle.api.user

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dto.user.TagRelationDto
import com.wini.owle.entity.composteid.TagRelationIdFactory
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import io.ktor.server.application.*
import io.ktor.util.*
import org.jetbrains.exposed.dao.id.CompositeID


class TagRelationApi(
    dll: DLL<CompositeID, TagRelationDto>
) : CompositeIdEndpointImpl<TagRelationDto>(
    dll = dll,
    serializer = TagRelationDto.serializer(),
    compositeIdFactory = TagRelationIdFactory,
    validator = { _-> true }
) {

    override fun ApplicationCall.readId(): CompositeID? {
        return compositeIdFactory.fromRequestParams(params = parameters.toMap())
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tag-relation") {
                call.asyncResultList(getAll())
            }
            get("/api/tag-relation/id?parentTagId={parentTagId}&childTagId={childTagId}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/tag-relation") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/tag-relation/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/tag-relation") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/tag-relation/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/tag-relation/id?parentTagId={parentTagId}&childTagId={childTagId}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/tag-relation/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }

}