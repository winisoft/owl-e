package com.wini.owle.api.user

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.user.UserDll
import com.wini.owle.dto.user.UserDto
import io.ktor.server.routing.*

class UserApi(dll: UserDll) : GenericLongPkApi<UserDto>(
    dll = dll,
    clazz = UserDto::class,
    serializer = UserDto.serializer()
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/user") { getAll(context.request.call) }
            get("/api/user/{id}") { getById(context.request.call) }
            post("/api/user") { create(context.request.call) }
            post("/api/user/batch") { createBatch(context.request.call) }
            put("/api/user") { update(context.request.call) }
            put("/api/user/batch") { updateBatch(context.request.call) }
            delete("/api/user/{id}") { super.delete(context.request.call) }
            delete("/api/user/batch") { deleteBatch(context.request.call) }
        }
    }
}