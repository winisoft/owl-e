package com.wini.owle.api.runs

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.runs.TestInputDll
import com.wini.owle.dto.TestInputDto
import io.ktor.server.routing.*

class TestInputApi(dll: TestInputDll) : GenericLongPkApi<TestInputDto>(
    dll = dll,
    clazz = TestInputDto::class,
    serializer = TestInputDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-input") { getAll(context.request.call) }
            get("/api/test-input/{id}") { getById(context.request.call) }
            post("/api/test-input") { create(context.request.call) }
            post("/api/test-input/batch") { createBatch(context.request.call) }
            put("/api/test-input") { update(context.request.call) }
            put("/api/test-input/batch") { updateBatch(context.request.call) }
            delete("/api/test-input/{id}") { super.delete(context.request.call) }
            delete("/api/test-input/batch") { deleteBatch(context.request.call) }
        }
    }
}