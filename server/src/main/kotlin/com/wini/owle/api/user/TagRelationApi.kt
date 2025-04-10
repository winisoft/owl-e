package com.wini.owle.api.user

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dto.user.TagRelationDto
import com.wini.owle.entity.composteid.TagRelationIdFactory
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import io.ktor.server.application.*
import io.ktor.util.*
import org.jetbrains.exposed.dao.id.CompositeID
import com.wini.owle.api.OwleApiCall
import com.wini.owle.async.AsyncResult


class TagRelationApi(
    dll: DLL<CompositeID, TagRelationDto>,
    validator: (TagRelationDto) -> Boolean = { _-> true}
): CompositeIdEndpointImpl<TagRelationDto>(
    dll = dll,
    serializer = TagRelationDto.serializer(),
    compositeIdFactory = TagRelationIdFactory,
    validator = validator
) {

    override fun ApplicationCall.readId(): AsyncResult<CompositeID> {
        return compositeIdFactory.fromRequestParams(params = parameters.toMap())
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tag-relation") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/tag-relation/id?parentTagId={parentTagId}&childTagId={childTagId}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/tag-relation") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/tag-relation/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/tag-relation") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/tag-relation/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/tag-relation/id?parentTagId={parentTagId}&childTagId={childTagId}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/tag-relation/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }

}