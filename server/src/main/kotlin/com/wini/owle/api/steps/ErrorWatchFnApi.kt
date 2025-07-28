package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatchFn
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class ErrorWatchFnApi(
    dll: DLL<Long, ErrorWatchFn>,
    validator: DtoValidator<ErrorWatchFn>
): LongIdEndpointImpl<ErrorWatchFn>(
    dll = dll,
    serializer = ErrorWatchFn.serializer(),
    validator = validator
), IErrorWatchFnApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("api/error-watch-fn") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("api/error-watch-fn/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("api/error-watch-fn") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("api/error-watch-fn/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("api/error-watch-fn") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("api/error-watch-fn/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("api/error-watch-fn/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("api/error-watch-fn/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}