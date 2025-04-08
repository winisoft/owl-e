package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.UserDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class UserApi(
    dll: DLL<Long, UserDto>
) : LongIdEndpointImpl<UserDto>(
    dll = dll,
    serializer = UserDto.serializer(),
    validator = { _-> true }
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/user") {
                call.asyncResultList(getAll())
            }
            get("/api/user/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/user") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/user/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/user") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/user/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/user/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/user/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}