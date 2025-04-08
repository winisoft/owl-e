package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


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
                call.asyncResultList(getAll())
            }
            get("/api/test-expected-outcome/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/test-expected-outcome") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/test-expected-outcome/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/test-expected-outcome") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/test-expected-outcome/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/test-expected-outcome/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/test-expected-outcome/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}