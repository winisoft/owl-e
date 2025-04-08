package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class TestExpectedOutcomeApi(
    dll: DLL<Long,TestExpectedOutcomeDto >
) : LongIdEndpointImpl<TestExpectedOutcomeDto>(
    dll = dll,
    serializer = TestExpectedOutcomeDto.serializer(),
    validator = { _-> true }
)  {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-expected-outcome") {
                call.asyncResultList(getAll())
            }
            get("/api/test-expected-outcome/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/test-expected-outcome") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/test-expected-outcome/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/test-expected-outcome") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/test-expected-outcome/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/test-expected-outcome/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/test-expected-outcome/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}