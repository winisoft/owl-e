package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.UserDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class UserApi(
    dll: DLL<Long, UserDto>,
    validator: (UserDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<UserDto>(
    dll = dll,
    serializer = UserDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/user") {
                call.asyncResultList(getAll())
            }
            get("/api/user/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/user") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/user/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/user") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/user/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/user/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/user/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}