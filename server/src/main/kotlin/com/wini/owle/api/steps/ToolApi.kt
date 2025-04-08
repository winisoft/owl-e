package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.ToolDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class ToolApi(
    dll: DLL<Long, ToolDto>
) : LongIdEndpointImpl<ToolDto>(
    dll = dll,
    serializer = ToolDto.serializer()
) {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool") {
                call.asyncResultList(getAll())
            }
            get("/api/tool/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/tool") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/tool/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/tool") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/tool/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/tool/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/tool/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}