package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.runs.TestCaseDll
import com.wini.owle.dto.runs.TestCaseDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class TestCaseApi(
    dll: TestCaseDll
) : LongIdEndpointImpl<TestCaseDto>(
    dll = dll,
    serializer = TestCaseDto.serializer(),
    validator = { _-> true}
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<TestCaseDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/test-case") {
                call.asyncResult(getAll())
            }
            get("/api/test-case/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/test-case") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/test-case/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/test-case") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/test-case/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/test-case/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/test-case/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}