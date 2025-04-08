package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatchFnDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class ErrorWatchFnApi(
    dll: DLL<Long, ErrorWatchFnDto>,
    validator: (ErrorWatchFnDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<ErrorWatchFnDto>(
    dll = dll,
    serializer = ErrorWatchFnDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("api/error-watch-fn") {
                call.asyncResultList(getAll())
            }
            get("api/error-watch-fn/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("api/error-watch-fn") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("api/error-watch-fn/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("api/error-watch-fn") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("api/error-watch-fn/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("api/error-watch-fn/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("api/error-watch-fn/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}