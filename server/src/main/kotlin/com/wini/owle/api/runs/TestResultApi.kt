package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestResultDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class TestResultApi(
    dll: DLL<Long, TestResultDto>,
    validator: DtoValidator<TestResultDto>
): LongIdEndpointImpl<TestResultDto>(
    dll = dll,
    serializer = TestResultDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-result") {
                call.execute(OwleApiCall.GetMany(TestResultDto::class))
            }
            get("/api/test-result/{id}") {
                call.execute(OwleApiCall.GetOne(TestResultDto::class))
            }
            post("/api/test-result") {
                call.execute(OwleApiCall.CreateOne(TestResultDto::class))
            }
            post("/api/test-result/batch") {
                call.execute(OwleApiCall.CreateMany(TestResultDto::class))
            }
            put("/api/test-result") {
                call.execute(OwleApiCall.UpdateOne(TestResultDto::class))
            }
            put("/api/test-result/batch") {
                call.execute(OwleApiCall.UpdateMany(TestResultDto::class))
            }
            delete("/api/test-result/{id}") {
                call.execute(OwleApiCall.DeleteOne(TestResultDto::class))
            }
            delete("/api/test-result/batch") {
                call.execute(OwleApiCall.DeleteMany(TestResultDto::class))
            }
        }
    }
}