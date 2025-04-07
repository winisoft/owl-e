package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.steps.PreReqInjectionDll
import com.wini.owle.dto.steps.PreReqInjectionDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class PreReqInjectionApi(
    dll: PreReqInjectionDll
): LongIdEndpointImpl<PreReqInjectionDto>(
    dll = dll,
    serializer = PreReqInjectionDto.serializer()
) {


    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/prereq-injection") {
                call.asyncResultMany(getAll())
            }
            get("/api/prereq-injection/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/prereq-injection") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/prereq-injection/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/prereq-injection") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/prereq-injection/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/prereq-injection/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/prereq-injection/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}