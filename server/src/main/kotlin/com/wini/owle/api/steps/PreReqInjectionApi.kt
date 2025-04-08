package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.PreReqInjectionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class PreReqInjectionApi(
    dll: DLL<Long, PreReqInjectionDto>,
    validator: (PreReqInjectionDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<PreReqInjectionDto>(
    dll = dll,
    serializer = PreReqInjectionDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/prereq-injection") {
                call.asyncResultList(getAll())
            }
            get("/api/prereq-injection/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/prereq-injection") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/prereq-injection/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/prereq-injection") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/prereq-injection/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/prereq-injection/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/prereq-injection/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}