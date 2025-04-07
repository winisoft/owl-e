package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.user.UserDll
import com.wini.owle.dto.user.UserDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

class UserApi(
    dll: UserDll
) : LongIdEndpointImpl<UserDto>(
    dll = dll,
    serializer = UserDto.serializer(),
    validator = { _-> true }
) {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/user") {
                call.asyncResultMany(getAll())
            }
            get("/api/user/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/user") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/user/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/user") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/user/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/user/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/user/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}