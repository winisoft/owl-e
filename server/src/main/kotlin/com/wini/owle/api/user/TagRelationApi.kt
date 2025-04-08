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
    dll: DLL<CompositeID, TagRelationDto>,
    validator: (TagRelationDto) -> Boolean = { _-> true}
): CompositeIdEndpointImpl<TagRelationDto>(
    dll = dll,
    serializer = TagRelationDto.serializer(),
    compositeIdFactory = TagRelationIdFactory,
    validator = validator
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
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/tag-relation") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/tag-relation/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/tag-relation") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/tag-relation/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/tag-relation/id?parentTagId={parentTagId}&childTagId={childTagId}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/tag-relation/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }

}