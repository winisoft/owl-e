package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall



class TestExpectedOutcomeApi(
    dll: DLL<Long,TestExpectedOutcomeDto>,
    validator: (TestExpectedOutcomeDto) -> Boolean = { _-> true }
) : LongIdEndpointImpl<TestExpectedOutcomeDto>(
    dll = dll,
    serializer = TestExpectedOutcomeDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-expected-outcome") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/test-expected-outcome/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/test-expected-outcome") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/test-expected-outcome/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/test-expected-outcome") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/test-expected-outcome/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/test-expected-outcome/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/test-expected-outcome/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}