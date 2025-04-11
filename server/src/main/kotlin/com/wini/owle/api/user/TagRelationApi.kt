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
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.validators.DtoValidator


class TagRelationApi(
    dll: DLL<CompositeID, TagRelationDto>,
    validator: DtoValidator<TagRelationDto>,
    compositeIdFactory: CompositeIdFactory<TagRelationDto>
): CompositeIdEndpointImpl<TagRelationDto>(
    dll = dll,
    serializer = TagRelationDto.serializer(),
    compositeIdFactory = compositeIdFactory,
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tag-relation") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/tag-relation/id") {
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
            delete("/api/tag-relation/id") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/tag-relation/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }

}