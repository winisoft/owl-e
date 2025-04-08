package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.StepExecutionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class StepExecutionApi(
    dll: DLL<Long, StepExecutionDto>,
    validator: (StepExecutionDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<StepExecutionDto>(
    dll = dll,
    serializer = StepExecutionDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step-execution") {
                call.asyncResultList(getAll())
            }
            get("/api/step-execution/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/step-execution") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/step-execution/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/step-execution") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/step-execution/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/step-execution/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/step-execution/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}