package com.wini.owle.api.runs.test

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.test.TestCase
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class TestCaseApi(
    dll: DLL<Long, TestCase>,
    validator: DtoValidator<TestCase>
): LongIdEndpointImpl<TestCase>(
    dll = dll,
    serializer = TestCase.serializer(),
    validator = validator
), ITestCaseApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-case") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/test-case/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/test-case") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/test-case/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/test-case") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/test-case/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/test-case/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) }
            }
            delete("/api/test-case/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}