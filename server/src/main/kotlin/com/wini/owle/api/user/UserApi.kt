package com.wini.owle.api.user

import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.User
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator


class UserApi(
    dll: DLL<Long, User>,
    validator: DtoValidator<User>
): LongIdEndpointImpl<User>(
    dll = dll,
    serializer = User.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/user") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/user/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/user") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/user/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/user") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/user/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/user/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/user/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}