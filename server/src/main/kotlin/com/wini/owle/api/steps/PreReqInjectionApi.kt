package com.wini.owle.api.steps

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.steps.PreReqInjectionDll
import com.wini.owle.dto.steps.PreReqInjectionDto
import io.ktor.server.routing.*

class PreReqInjectionApi(dll: PreReqInjectionDll): GenericLongPkApi<PreReqInjectionDto>(
    dll = dll,
    clazz = PreReqInjectionDto::class,
    serializer = PreReqInjectionDto.serializer()
) {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/prereq-injection") { getAll(context.request.call) }
            get("/api/prereq-injection/{id}") { getById(context.request.call) }
            post("/api/prereq-injection") { create(context.request.call) }
            post("/api/prereq-injection/batch") { createBatch(context.request.call) }
            put("/api/prereq-injection") { update(context.request.call) }
            put("/api/prereq-injection/batch") { updateBatch(context.request.call) }
            delete("/api/prereq-injection/{id}") { super.delete(context.request.call) }
            delete("/api/prereq-injection/batch") { deleteBatch(context.request.call) }
        }
    }
}