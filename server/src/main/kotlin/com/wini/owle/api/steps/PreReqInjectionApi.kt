package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.InitialDataPointInjector
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class PreReqInjectionApi(
    dll: DLL<Long, InitialDataPointInjector>,
    validator: DtoValidator<InitialDataPointInjector>
): LongIdEndpointImpl<InitialDataPointInjector>(
    dll = dll,
    serializer = InitialDataPointInjector.serializer(),
    validator = validator
), IPreReqInjectionApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/prereq-injection") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/prereq-injection/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/prereq-injection") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/prereq-injection/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/prereq-injection") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/prereq-injection/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/prereq-injection/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/prereq-injection/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}