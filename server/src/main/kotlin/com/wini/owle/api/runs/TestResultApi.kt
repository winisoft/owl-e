package com.wini.owle.api.runs

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.runs.TestResultDll
import com.wini.owle.dto.runs.TestResultDto
import io.ktor.server.routing.*

class TestResultApi(dll: TestResultDll) : GenericLongPkApi<TestResultDto>(
    dll = dll,
    clazz = TestResultDto::class,
    serializer = TestResultDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-result") { getAll(context.request.call) }
            get("/api/test-result/{id}") { getById(context.request.call) }
            post("/api/test-result") { create(context.request.call) }
            post("/api/test-result/batch") { createBatch(context.request.call) }
            put("/api/test-result") { update(context.request.call) }
            put("/api/test-result/batch") { updateBatch(context.request.call) }
            delete("/api/test-result/{id}") { super.delete(context.request.call) }
            delete("/api/test-result/batch") { deleteBatch(context.request.call) }
        }
    }
}