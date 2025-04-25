package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatchFn
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator


class ErrorWatchFnApi(
    dll: DLL<Long, ErrorWatchFn>,
    validator: DtoValidator<ErrorWatchFn>
): LongIdEndpointImpl<ErrorWatchFn>(
    dll = dll,
    serializer = ErrorWatchFn.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("api/error-watch-fn") {
                call.execute(OwleApiCall.GetMany)
            }
            get("api/error-watch-fn/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("api/error-watch-fn") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("api/error-watch-fn/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("api/error-watch-fn") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("api/error-watch-fn/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("api/error-watch-fn/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("api/error-watch-fn/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}