package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.runs.TestInputDll
import com.wini.owle.dto.runs.TestInputDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class TestInputApi(
    dll: TestInputDll
) : LongIdEndpointImpl<TestInputDto>(
    dll = dll,
    serializer = TestInputDto.serializer()
)  {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-input") {
                call.asyncResultMany(getAll())
            }
            get("/api/test-input/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/test-input") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/test-input/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/test-input") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/test-input/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/test-input/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/test-input/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}