package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.ToolDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class ToolApi(
    dll: DLL<Long, ToolDto>,
    validator: (ToolDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<ToolDto>(
    dll = dll,
    serializer = ToolDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool") {
                call.asyncResultList(getAll())
            }
            get("/api/tool/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/tool") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/tool/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/tool") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/tool/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/tool/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/tool/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}