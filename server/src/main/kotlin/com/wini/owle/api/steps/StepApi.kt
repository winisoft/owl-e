package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.StepDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class StepApi(
    dll: DLL<Long, StepDto>,
    validator: (StepDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<StepDto>(
    dll = dll,
    serializer = StepDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step") {
                call.asyncResultList(getAll())
            }
            get("/api/step/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/step") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/step/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/step") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/step/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/step/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/step/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}