package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.steps.ErrorWatchFnDll
import com.wini.owle.dto.steps.ErrorWatchFnDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class ErrorWatchFnApi(
    dll: ErrorWatchFnDll
) : LongIdEndpointImpl<ErrorWatchFnDto>(
    dll = dll,
    serializer = ErrorWatchFnDto.serializer()
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<ErrorWatchFnDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("api/error-watch-fn") {
                call.asyncResult(getAll())
            }
            get("api/error-watch-fn/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("api/error-watch-fn") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("api/error-watch-fn/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("api/error-watch-fn") {
                call.asyncResultSingle(update(call, validator))
            }
            put("api/error-watch-fn/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("api/error-watch-fn/{id}") {
                call.asyncResult(delete(call))
            }
            delete("api/error-watch-fn/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}