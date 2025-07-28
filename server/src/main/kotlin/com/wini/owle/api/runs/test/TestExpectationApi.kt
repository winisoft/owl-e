package com.wini.owle.api.runs.test

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.TestExpectation
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TestExpectationApi(
    dll: DLL<Long, TestExpectation>,
    validator: DtoValidator<TestExpectation>
) : LongIdEndpointImpl<TestExpectation>(
    dll = dll,
    serializer = TestExpectation.serializer(),
    validator = validator
), ITestExpectationApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-expected-outcome") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/test-expected-outcome/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/test-expected-outcome") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/test-expected-outcome/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/test-expected-outcome") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/test-expected-outcome/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/test-expected-outcome/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) }
            }
            delete("/api/test-expected-outcome/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}