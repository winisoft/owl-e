package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ErrorWatchFnDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class ErrorWatchFnApi(
    dll: DLL<Long, ErrorWatchFnDto>
) : LongIdEndpointImpl<ErrorWatchFnDto>(
    dll = dll,
    serializer = ErrorWatchFnDto.serializer()
)  {



    override fun register(routing: Routing) {
        routing.apply {
            get("api/error-watch-fn") {
                call.asyncResultList(getAll())
            }
            get("api/error-watch-fn/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("api/error-watch-fn") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("api/error-watch-fn/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("api/error-watch-fn") {
                call.asyncResultSingle(update(call, validator))
            }
            put("api/error-watch-fn/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("api/error-watch-fn/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("api/error-watch-fn/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}