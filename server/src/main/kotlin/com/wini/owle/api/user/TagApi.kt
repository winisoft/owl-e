package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.user.TagDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class TagApi(
    dll: DLL<Long, TagDto>,
    validator: (TagDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TagDto>(
    dll = dll,
    serializer = TagDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tag") {
                call.asyncResultList(getAll())
            }
            get("/api/tag/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/tag") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/tag/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/tag") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/tag/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/tag/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/tag/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}