package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.runs.StepExecutionDll
import com.wini.owle.dto.runs.StepExecutionDto
import com.wini.owle.dto.runs.TestCaseDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

class StepExecutionApi(
    dll: StepExecutionDll
) : LongIdEndpointImpl<StepExecutionDto>(
    dll = dll,
    serializer = StepExecutionDto.serializer(),
    validator = { _-> true }
)  {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step-execution") {
                call.asyncResultMany(getAll())
            }
            get("/api/step-execution/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/step-execution") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/step-execution/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/step-execution") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/step-execution/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/step-execution/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/step-execution/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}