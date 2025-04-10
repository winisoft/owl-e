package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.StepDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator


class StepApi(
    dll: DLL<Long, StepDto>,
    validator: DtoValidator<StepDto>
): LongIdEndpointImpl<StepDto>(
    dll = dll,
    serializer = StepDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step") {
                call.execute(OwleApiCall.GetMany(StepDto::class))
            }
            get("/api/step/{id}") {
                call.execute(OwleApiCall.GetOne(StepDto::class))
            }
            post("/api/step") {
                call.execute(OwleApiCall.CreateOne(StepDto::class))
            }
            post("/api/step/batch") {
                call.execute(OwleApiCall.CreateMany(StepDto::class))
            }
            put("/api/step") {
                call.execute(OwleApiCall.UpdateOne(StepDto::class))
            }
            put("/api/step/batch") {
                call.execute(OwleApiCall.UpdateMany(StepDto::class))
            }
            delete("/api/step/{id}") {
                call.execute(OwleApiCall.DeleteOne(StepDto::class)) 
            }
            delete("/api/step/batch") {
                call.execute(OwleApiCall.DeleteMany(StepDto::class))
            }
        }
    }
}