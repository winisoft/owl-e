package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.user.tags.Tag
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl

class TagApiImpl(
    dll: DLL<Long, Tag>,
    validator: DtoValidator<Tag>
): LongIdEndpointImpl<Tag>(
    dll = dll,
    serializer = Tag.serializer(),
    validator = validator
), ITagApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tag") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/tag/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/tag") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/tag/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/tag") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/tag/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/tag/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/tag/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}