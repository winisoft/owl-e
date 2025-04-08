package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.TestCaseDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL

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
                call.asyncResultList(getAll())
            }
            get("/api/test-case/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/test-case") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/test-case/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/test-case") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/test-case/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/test-case/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/test-case/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}