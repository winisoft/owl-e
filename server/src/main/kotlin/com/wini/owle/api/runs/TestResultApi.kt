package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestResultDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class TestResultApi(
    dll: DLL<Long, TestResultDto>
) : LongIdEndpointImpl<TestResultDto>(
    dll = dll,
    serializer = TestResultDto.serializer()
)  {


    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-result") {
                call.asyncResultList(getAll())
            }
            get("/api/test-result/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/test-result") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/test-result/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/test-result") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/test-result/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/test-result/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/test-result/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}