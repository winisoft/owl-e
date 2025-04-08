package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestResultDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class TestResultApi(
    dll: DLL<Long, TestResultDto>,
    validator: (TestResultDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TestResultDto>(
    dll = dll,
    serializer = TestResultDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-result") {
                call.asyncResultList(getAll())
            }
            get("/api/test-result/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/test-result") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/test-result/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/test-result") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/test-result/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/test-result/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/test-result/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}