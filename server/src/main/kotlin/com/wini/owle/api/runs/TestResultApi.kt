package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.runs.TestResultDll
import com.wini.owle.dto.runs.TestResultDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class TestResultApi(
    dll: TestResultDll
) : LongIdEndpointImpl<TestResultDto>(
    dll = dll,
    serializer = TestResultDto.serializer()
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<TestResultDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }
    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-result") {
                call.asyncResult(getAll())
            }
            get("/api/test-result/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/test-result") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/test-result/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/test-result") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/test-result/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/test-result/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/test-result/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}