package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.workflows.ToolDll
import com.wini.owle.dto.workflows.ToolDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class ToolApi(
    dll: ToolDll
) : LongIdEndpointImpl<ToolDto>(
    dll = dll,
    serializer = ToolDto.serializer()
) {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<ToolDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool") {
                call.asyncResult(getAll())
            }
            get("/api/tool/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/tool") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/tool/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/tool") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/tool/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/tool/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/tool/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}