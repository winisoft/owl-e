package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.user.IUserApi
import com.wini.owle.dll.DLL
import com.wini.owle.dto.user.User
import com.wini.owle.validators.DtoValidator
import io.ktor.server.application.*
import io.ktor.server.routing.*


class UserApi(
    dll: DLL<Long, User>,
    validator: DtoValidator<User>
) : LongIdEndpointImpl<User>(
    dll = dll,
    serializer = User.serializer(),
    validator = validator
), IUserApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/user") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/user/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/user") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/user/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/user") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/user/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/user/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/user/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}