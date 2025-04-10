package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatchFnDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator


class ErrorWatchFnApi(
    dll: DLL<Long, ErrorWatchFnDto>,
    validator: DtoValidator<ErrorWatchFnDto>
): LongIdEndpointImpl<ErrorWatchFnDto>(
    dll = dll,
    serializer = ErrorWatchFnDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("api/error-watch-fn") {
                call.execute(OwleApiCall.GetMany(ErrorWatchFnDto::class))
            }
            get("api/error-watch-fn/{id}") {
                call.execute(OwleApiCall.GetOne(ErrorWatchFnDto::class))
            }
            post("api/error-watch-fn") {
                call.execute(OwleApiCall.CreateOne(ErrorWatchFnDto::class))
            }
            post("api/error-watch-fn/batch") {
                call.execute(OwleApiCall.CreateMany(ErrorWatchFnDto::class))
            }
            put("api/error-watch-fn") {
                call.execute(OwleApiCall.UpdateOne(ErrorWatchFnDto::class))
            }
            put("api/error-watch-fn/batch") {
                call.execute(OwleApiCall.UpdateMany(ErrorWatchFnDto::class))
            }
            delete("api/error-watch-fn/{id}") {
                call.execute(OwleApiCall.DeleteOne(ErrorWatchFnDto::class)) 
            }
            delete("api/error-watch-fn/batch") {
                call.execute(OwleApiCall.DeleteMany(ErrorWatchFnDto::class))
            }
        }
    }
}