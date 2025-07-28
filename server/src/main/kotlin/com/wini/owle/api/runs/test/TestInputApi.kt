package com.wini.owle.api.runs.test

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.TestInput
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TestInputApi(
    dll: DLL<Long, TestInput>,
    validator: DtoValidator<TestInput>
): LongIdEndpointImpl<TestInput>(
    dll = dll,
    serializer = TestInput.serializer(),
    validator = validator
), ITestInputApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-input") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/test-input/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/test-input") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/test-input/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/test-input") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/test-input/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/test-input/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) }
            }
            delete("/api/test-input/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}