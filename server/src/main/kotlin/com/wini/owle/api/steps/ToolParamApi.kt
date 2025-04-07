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


    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-param") {
                call.asyncResultMany(getAll())
            }
            get("/api/tool-param/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/tool-param") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/tool-param/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/tool-param") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/tool-param/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
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