package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.TestInputDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class TestInputApi(
    dll: DLL<Long, TestInputDto>,
    validator: (TestInputDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TestInputDto>(
    dll = dll,
    serializer = TestInputDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-input") {
                call.asyncResultList(getAll())
            }
            get("/api/test-input/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/test-input") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/test-input/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/test-input") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/test-input/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/test-input/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/test-input/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}