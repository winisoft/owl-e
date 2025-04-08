package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestInputDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class TestInputApi(
    dll: DLL<Long, TestInputDto>
) : LongIdEndpointImpl<TestInputDto>(
    dll = dll,
    serializer = TestInputDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-input") {
                call.asyncResultList(getAll())
            }
            get("/api/test-input/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/test-input") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/test-input/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/test-input") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/test-input/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/test-input/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/test-input/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}