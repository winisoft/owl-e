package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.workflows.StepDll
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

class StepApi(
    dll: StepDll
) : LongIdEndpointImpl<StepDto>(
    dll = dll,
    serializer = StepDto.serializer(),
    validator = { _-> true }

) {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step") {
                call.asyncResultMany(getAll())
            }
            get("/api/step/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/step") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/step/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/step") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/step/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/step/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/step/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}