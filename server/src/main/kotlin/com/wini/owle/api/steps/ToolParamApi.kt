package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.workflows.ToolParamDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
class ToolParamApi(
    dll: DLL<Long, ToolParamDto>
) : LongIdEndpointImpl<ToolParamDto>(
    dll = dll,
    serializer = ToolParamDto.serializer()
) {


    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-param") {
                call.asyncResultList(getAll())
            }
            get("/api/tool-param/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/tool-param") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/tool-param/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/tool-param") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/tool-param/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/tool-param/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/tool-param/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}