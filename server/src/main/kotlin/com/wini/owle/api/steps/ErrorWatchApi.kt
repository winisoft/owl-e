package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatchDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class ErrorWatchApi(
    dll: DLL<Long, ErrorWatchDto>,
    validator: (ErrorWatchDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<ErrorWatchDto>(
    dll = dll,
    serializer = ErrorWatchDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/error-watch") {
                call.asyncResultList(getAll())
            }
            get("/api/error-watch/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/error-watch") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/error-watch/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/error-watch") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/error-watch/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/error-watch/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/error-watch/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}