package com.wini.owle.api.runs

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.runs.TestCaseDll
import com.wini.owle.dto.runs.TestCaseDto
import io.ktor.server.routing.*

class TestCaseApi(dll: TestCaseDll) : GenericLongPkApi<TestCaseDto>(
    dll = dll,
    clazz = TestCaseDto::class,
    serializer = TestCaseDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-case") { getAll(context.request.call) }
            get("/api/test-case/{id}") { getById(context.request.call) }
            post("/api/test-case") { create(context.request.call) }
            post("/api/test-case/batch") { createBatch(context.request.call) }
            put("/api/test-case") { update(context.request.call) }
            put("/api/test-case/batch") { updateBatch(context.request.call) }
            delete("/api/test-case/{id}") { super.delete(context.request.call) }
            delete("/api/test-case/batch") { deleteBatch(context.request.call) }
        }
    }
}