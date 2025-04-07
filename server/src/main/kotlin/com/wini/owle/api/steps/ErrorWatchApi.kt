package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.steps.ErrorWatchDll
import com.wini.owle.dto.steps.ErrorWatchDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class ErrorWatchApi(
    dll: ErrorWatchDll
) : LongIdEndpointImpl<ErrorWatchDto>(
    dll = dll,
    serializer = ErrorWatchDto.serializer()
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<ErrorWatchDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }
    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/error-watch") {
                call.asyncResult(getAll())
            }
            get("/api/error-watch/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/error-watch") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/error-watch/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/error-watch") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/error-watch/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/error-watch/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/error-watch/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}