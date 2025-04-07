package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.TestCaseDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL

class TestCaseApi(
    dll: DLL<Long, TestCaseDto>
) : LongIdEndpointImpl<TestCaseDto>(
    dll = dll,
    serializer = TestCaseDto.serializer(),
    validator = { _-> true}
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-case") {
                call.asyncResultMany(getAll())
            }
            get("/api/test-case/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/test-case") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/test-case/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/test-case") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/test-case/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/test-case/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/test-case/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}