package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.TestCaseDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall


class TestCaseApi(
    dll: DLL<Long, TestCaseDto>,
    validator: (TestCaseDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TestCaseDto>(
    dll = dll,
    serializer = TestCaseDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-case") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/test-case/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/test-case") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/test-case/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/test-case") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/test-case/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/test-case/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/test-case/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}