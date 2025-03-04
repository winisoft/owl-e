package com.wini.owle.api.runs

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.runs.TestExpectedOutcomeDll
import com.wini.owle.dto.TestExpectedOutcomeDto
import io.ktor.server.routing.*

class TestExpectedOutcomeApi(dll: TestExpectedOutcomeDll) : GenericLongPkApi<TestExpectedOutcomeDto>(
    dll = dll,
    clazz = TestExpectedOutcomeDto::class,
    serializer = TestExpectedOutcomeDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-expected-outcome") { getAll(context.request.call) }
            get("/api/test-expected-outcome/{id}") { getById(context.request.call) }
            post("/api/test-expected-outcome") { create(context.request.call) }
            post("/api/test-expected-outcome/batch") { createBatch(context.request.call) }
            put("/api/test-expected-outcome") { update(context.request.call) }
            put("/api/test-expected-outcome/batch") { updateBatch(context.request.call) }
            delete("/api/test-expected-outcome/{id}") { super.delete(context.request.call) }
            delete("/api/test-expected-outcome/batch") { deleteBatch(context.request.call) }
        }
    }
}