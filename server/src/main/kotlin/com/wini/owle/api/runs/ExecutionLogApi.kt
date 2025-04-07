package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.environs.PackageRepoDll
import com.wini.owle.dll.runs.ExecutionLogDll
import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.dto.runs.BenchmarkingDataDto
import com.wini.owle.dto.runs.ExecutionLogDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer


class ExecutionLogApi(
    dll: ExecutionLogDll
) : LongIdEndpointImpl<ExecutionLogDto>(
    dll = dll,
    serializer = ExecutionLogDto.serializer()
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/execution-log") {
                call.asyncResultMany(getAll())
            }
            get("/api/execution-log/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/execution-log") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/execution-log/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/execution-log") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/execution-log/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/execution-log/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/execution-log/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}