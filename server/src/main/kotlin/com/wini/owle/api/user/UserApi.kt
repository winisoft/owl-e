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

class UserApi(
    dll: UserDll
) : LongIdEndpointImpl<UserDto>(
    dll = dll,
    serializer = UserDto.serializer(),
    validator = { _-> true }
) {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<UserDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/user") {
                call.asyncResult(getAll())
            }
            get("/api/user/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/user") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/user/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/user") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/user/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
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