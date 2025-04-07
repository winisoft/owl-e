package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.environs.PackageRepoDll
import com.wini.owle.dll.runs.BenchmarkingDataDll
import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.dto.runs.BenchmarkingDataDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

class BenchmarkingDataApi(
    dll: BenchmarkingDataDll
) : LongIdEndpointImpl<BenchmarkingDataDto>(
    dll = dll,
    serializer = BenchmarkingDataDto.serializer(),
    validator = { _-> true }
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/benchmarking-data") {
                call.asyncResultMany(getAll())
            }
            get("/api/benchmarking-data/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/benchmarking-data") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/benchmarking-data/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/benchmarking-data") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/benchmarking-data/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/benchmarking-data/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/benchmarking-data/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}