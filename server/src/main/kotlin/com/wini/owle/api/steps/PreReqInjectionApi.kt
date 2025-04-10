package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.PreReqInjectionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator


class PreReqInjectionApi(
    dll: DLL<Long, PreReqInjectionDto>,
    validator: DtoValidator<PreReqInjectionDto>
): LongIdEndpointImpl<PreReqInjectionDto>(
    dll = dll,
    serializer = PreReqInjectionDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/prereq-injection") {
                call.execute(OwleApiCall.GetMany(PreReqInjectionDto::class))
            }
            get("/api/prereq-injection/{id}") {
                call.execute(OwleApiCall.GetOne(PreReqInjectionDto::class))
            }
            post("/api/prereq-injection") {
                call.execute(OwleApiCall.CreateOne(PreReqInjectionDto::class))
            }
            post("/api/prereq-injection/batch") {
                call.execute(OwleApiCall.CreateMany(PreReqInjectionDto::class))
            }
            put("/api/prereq-injection") {
                call.execute(OwleApiCall.UpdateOne(PreReqInjectionDto::class))
            }
            put("/api/prereq-injection/batch") {
                call.execute(OwleApiCall.UpdateMany(PreReqInjectionDto::class))
            }
            delete("/api/prereq-injection/{id}") {
                call.execute(OwleApiCall.DeleteOne(PreReqInjectionDto::class)) 
            }
            delete("/api/prereq-injection/batch") {
                call.execute(OwleApiCall.DeleteMany(PreReqInjectionDto::class))
            }
        }
    }
}