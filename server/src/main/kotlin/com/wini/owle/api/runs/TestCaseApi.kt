package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.TestCaseDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator


class TestCaseApi(
    dll: DLL<Long, TestCaseDto>,
    validator: DtoValidator<TestCaseDto>
): LongIdEndpointImpl<TestCaseDto>(
    dll = dll,
    serializer = TestCaseDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-case") {
                call.execute(OwleApiCall.GetMany(TestCaseDto::class))
            }
            get("/api/test-case/{id}") {
                call.execute(OwleApiCall.GetOne(TestCaseDto::class))
            }
            post("/api/test-case") {
                call.execute(OwleApiCall.CreateOne(TestCaseDto::class))
            }
            post("/api/test-case/batch") {
                call.execute(OwleApiCall.CreateMany(TestCaseDto::class))
            }
            put("/api/test-case") {
                call.execute(OwleApiCall.UpdateOne(TestCaseDto::class))
            }
            put("/api/test-case/batch") {
                call.execute(OwleApiCall.UpdateMany(TestCaseDto::class))
            }
            delete("/api/test-case/{id}") {
                call.execute(OwleApiCall.DeleteOne(TestCaseDto::class))
            }
            delete("/api/test-case/batch") {
                call.execute(OwleApiCall.DeleteMany(TestCaseDto::class))
            }
        }
    }
}