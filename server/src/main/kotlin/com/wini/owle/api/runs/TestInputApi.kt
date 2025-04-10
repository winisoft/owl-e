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
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/test-input/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/test-input") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/test-input/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/test-input") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/test-input/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/test-input/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/test-input/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}