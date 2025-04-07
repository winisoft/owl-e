package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.workflows.ToolParamDll
import com.wini.owle.dto.workflows.ToolParamDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class ToolParamApi(
    dll: ToolParamDll
) : LongIdEndpointImpl<ToolParamDto>(
    dll = dll,
    serializer = ToolParamDto.serializer()
) {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<ToolParamDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }
    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-param") {
                call.asyncResult(getAll())
            }
            get("/api/tool-param/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/tool-param") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/tool-param/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/tool-param") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/tool-param/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/tool-param/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/tool-param/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}