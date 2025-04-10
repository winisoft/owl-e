package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TestExpectedOutcomeApi(
    dll: DLL<Long,TestExpectedOutcomeDto>,
    validator: DtoValidator<TestExpectedOutcomeDto>
) : LongIdEndpointImpl<TestExpectedOutcomeDto>(
    dll = dll,
    serializer = TestExpectedOutcomeDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-expected-outcome") {
                call.execute(OwleApiCall.GetMany(TestExpectedOutcomeDto::class))
            }
            get("/api/test-expected-outcome/{id}") {
                call.execute(OwleApiCall.GetOne(TestExpectedOutcomeDto::class))
            }
            post("/api/test-expected-outcome") {
                call.execute(OwleApiCall.CreateOne(TestExpectedOutcomeDto::class))
            }
            post("/api/test-expected-outcome/batch") {
                call.execute(OwleApiCall.CreateMany(TestExpectedOutcomeDto::class))
            }
            put("/api/test-expected-outcome") {
                call.execute(OwleApiCall.UpdateOne(TestExpectedOutcomeDto::class))
            }
            put("/api/test-expected-outcome/batch") {
                call.execute(OwleApiCall.UpdateMany(TestExpectedOutcomeDto::class))
            }
            delete("/api/test-expected-outcome/{id}") {
                call.execute(OwleApiCall.DeleteOne(TestExpectedOutcomeDto::class))
            }
            delete("/api/test-expected-outcome/batch") {
                call.execute(OwleApiCall.DeleteMany(TestExpectedOutcomeDto::class))
            }
        }
    }
}