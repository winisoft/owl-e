package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.user.TagDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TagApi(
    dll: DLL<Long, TagDto>
) : LongIdEndpointImpl<TagDto>(
    dll = dll,
    serializer = TagDto.serializer(),
    validator = { _-> true }
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tag") {
                call.asyncResultList(getAll())
            }
            get("/api/tag/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/tag") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/tag/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/tag") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/tag/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/tag/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/tag/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}