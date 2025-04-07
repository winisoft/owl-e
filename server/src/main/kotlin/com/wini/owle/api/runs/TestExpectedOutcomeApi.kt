package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.runs.TestExpectedOutcomeDll
import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class TestExpectedOutcomeApi(
    dll: TestExpectedOutcomeDll
) : LongIdEndpointImpl<TestExpectedOutcomeDto>(
    dll = dll,
    serializer = TestExpectedOutcomeDto.serializer(),
    validator = { _-> true }
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<TestExpectedOutcomeDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-expected-outcome") {
                call.asyncResult(getAll())
            }
            get("/api/test-expected-outcome/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/test-expected-outcome") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/test-expected-outcome/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/test-expected-outcome") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/test-expected-outcome/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/test-expected-outcome/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/test-expected-outcome/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}