package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.StepExecutionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class StepExecutionApi(
    dll: DLL<Long, StepExecutionDto>,
    validator: DtoValidator<StepExecutionDto>
): LongIdEndpointImpl<StepExecutionDto>(
    dll = dll,
    serializer = StepExecutionDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step-execution") {
                call.execute(OwleApiCall.GetMany(StepExecutionDto::class))
            }
            get("/api/step-execution/{id}") {
                call.execute(OwleApiCall.GetOne(StepExecutionDto::class))
            }
            post("/api/step-execution") {
                call.execute(OwleApiCall.CreateOne(StepExecutionDto::class))
            }
            post("/api/step-execution/batch") {
                call.execute(OwleApiCall.CreateMany(StepExecutionDto::class))
            }
            put("/api/step-execution") {
                call.execute(OwleApiCall.UpdateOne(StepExecutionDto::class))
            }
            put("/api/step-execution/batch") {
                call.execute(OwleApiCall.UpdateMany(StepExecutionDto::class))
            }
            delete("/api/step-execution/{id}") {
                call.execute(OwleApiCall.DeleteOne(StepExecutionDto::class))
            }
            delete("/api/step-execution/batch") {
                call.execute(OwleApiCall.DeleteMany(StepExecutionDto::class))
            }
        }
    }
}