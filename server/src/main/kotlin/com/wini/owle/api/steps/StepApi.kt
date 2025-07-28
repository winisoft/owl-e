package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Step
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.workflows.IStepApi

class StepApi(
    dll: DLL<Long, Step>,
    validator: DtoValidator<Step>
): LongIdEndpointImpl<Step>(
    dll = dll,
    serializer = Step.serializer(),
    validator = validator
), IStepApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/step/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/step") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/step/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/step") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/step/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/step/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/step/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}