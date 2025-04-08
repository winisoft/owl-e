package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.ExecutionLogDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL

class ExecutionLogApi(
    dll: DLL<Long, ExecutionLogDto>,
    validator: (ExecutionLogDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<ExecutionLogDto>(
    dll = dll,
    serializer = ExecutionLogDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/execution-log") {
                call.asyncResultList(getAll())
            }
            get("/api/execution-log/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/execution-log") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/execution-log/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/execution-log") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/execution-log/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/execution-log/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/execution-log/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}