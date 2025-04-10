package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatchDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class ErrorWatchApi(
    dll: DLL<Long, ErrorWatchDto>,
    validator: DtoValidator<ErrorWatchDto>
): LongIdEndpointImpl<ErrorWatchDto>(
    dll = dll,
    serializer = ErrorWatchDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/error-watch") {
                call.execute(OwleApiCall.GetMany(ErrorWatchDto::class))
            }
            get("/api/error-watch/{id}") {
                call.execute(OwleApiCall.GetOne(ErrorWatchDto::class))
            }
            post("/api/error-watch") {
                call.execute(OwleApiCall.CreateOne(ErrorWatchDto::class))
            }
            post("/api/error-watch/batch") {
                call.execute(OwleApiCall.CreateMany(ErrorWatchDto::class))
            }
            put("/api/error-watch") {
                call.execute(OwleApiCall.UpdateOne(ErrorWatchDto::class))
            }
            put("/api/error-watch/batch") {
                call.execute(OwleApiCall.UpdateMany(ErrorWatchDto::class))
            }
            delete("/api/error-watch/{id}") {
                call.execute(OwleApiCall.DeleteOne(ErrorWatchDto::class)) 
            }
            delete("/api/error-watch/batch") {
                call.execute(OwleApiCall.DeleteMany(ErrorWatchDto::class))
            }
        }
    }
}