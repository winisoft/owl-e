package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.workflows.ToolParamDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
class ToolParamApi(
    dll: DLL<Long, ToolParamDto>,
    validator: (ToolParamDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<ToolParamDto>(
    dll = dll,
    serializer = ToolParamDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-param") {
                call.asyncResultList(getAll())
            }
            get("/api/tool-param/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/tool-param") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/tool-param/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/tool-param") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/tool-param/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/tool-param/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/tool-param/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}