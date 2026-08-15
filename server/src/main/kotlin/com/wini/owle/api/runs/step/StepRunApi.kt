package com.wini.owle.api.runs.step

import io.ktor.server.routing.*
import com.wini.owle.dll.DLL

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.schema.Schema
import io.ktor.server.application.call

class StepRunApi(
    dll: DLL<Schema>











































    eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee























































































































































































































































































































    <ULong, Schema.StepRun>,
    validator: DtoValidator<Schema.StepRun>
): ULongIdEndpointImpl<Schema.StepRun>(
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