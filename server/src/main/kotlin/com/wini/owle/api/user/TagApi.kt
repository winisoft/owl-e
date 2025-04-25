package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.user.tags.Tag
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TagApi(
    dll: DLL<Long, Tag>,
    validator: DtoValidator<Tag>
): LongIdEndpointImpl<Tag>(
    dll = dll,
    serializer = Tag.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tag") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/tag/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/tag") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/tag/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/tag") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/tag/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/tag/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/tag/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}