package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.StepExecutionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class StepExecutionApi(
    dll: DLL<Long, StepExecutionDto>
) : LongIdEndpointImpl<StepExecutionDto>(
    dll = dll,
    serializer = StepExecutionDto.serializer(),
    validator = { _-> true }
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step-execution") {
                call.asyncResultList(getAll())
            }
            get("/api/step-execution/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/step-execution") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/step-execution/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/step-execution") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/step-execution/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/step-execution/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/step-execution/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}