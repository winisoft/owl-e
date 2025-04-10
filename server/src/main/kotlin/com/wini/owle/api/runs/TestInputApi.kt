package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestInputDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator


class TestInputApi(
    dll: DLL<Long, TestInputDto>,
    validator: DtoValidator<TestInputDto>
): LongIdEndpointImpl<TestInputDto>(
    dll = dll,
    serializer = TestInputDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-input") {
                call.execute(OwleApiCall.GetMany(TestInputDto::class))
            }
            get("/api/test-input/{id}") {
                call.execute(OwleApiCall.GetOne(TestInputDto::class))
            }
            post("/api/test-input") {
                call.execute(OwleApiCall.CreateOne(TestInputDto::class))
            }
            post("/api/test-input/batch") {
                call.execute(OwleApiCall.CreateMany(TestInputDto::class))
            }
            put("/api/test-input") {
                call.execute(OwleApiCall.UpdateOne(TestInputDto::class))
            }
            put("/api/test-input/batch") {
                call.execute(OwleApiCall.UpdateMany(TestInputDto::class))
            }
            delete("/api/test-input/{id}") {
                call.execute(OwleApiCall.DeleteOne(TestInputDto::class))
            }
            delete("/api/test-input/batch") {
                call.execute(OwleApiCall.DeleteMany(TestInputDto::class))
            }
        }
    }
}