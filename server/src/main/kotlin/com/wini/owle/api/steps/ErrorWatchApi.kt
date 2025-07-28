package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatch
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class ErrorWatchApi(
    dll: DLL<Long, ErrorWatch>,
    validator: DtoValidator<ErrorWatch>
): LongIdEndpointImpl<ErrorWatch>(
    dll = dll,
    serializer = ErrorWatch.serializer(),
    validator = validator
), IErrorWatchApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/error-watch") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/error-watch/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/error-watch") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/error-watch/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/error-watch") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/error-watch/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/error-watch/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/error-watch/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}