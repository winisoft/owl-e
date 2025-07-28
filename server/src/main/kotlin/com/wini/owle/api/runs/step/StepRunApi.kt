package com.wini.owle.api.runs.step

import com.wini.owle.dto.runs.step.StepRun
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class StepRunApi(
    dll: DLL<Long, StepRun>,
    validator: DtoValidator<StepRun>
): LongIdEndpointImpl<StepRun>(
    dll = dll,
    serializer = StepRun.serializer(),
    validator = validator
), IStepRunApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step-execution") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/step-execution/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/step-execution") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/step-execution/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/step-execution") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/step-execution/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/step-execution/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) }
            }
            delete("/api/step-execution/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}