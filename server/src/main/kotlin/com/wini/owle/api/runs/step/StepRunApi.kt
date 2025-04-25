package com.wini.owle.api.runs.step

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.step.StepExecution
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class StepRunApi(
    dll: DLL<Long, StepExecution>,
    validator: DtoValidator<StepExecution>
): LongIdEndpointImpl<StepExecution>(
    dll = dll,
    serializer = StepExecution.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step-execution") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/step-execution/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/step-execution") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/step-execution/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/step-execution") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/step-execution/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/step-execution/{id}") {
                call.execute(OwleApiCall.DeleteOne)
            }
            delete("/api/step-execution/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}