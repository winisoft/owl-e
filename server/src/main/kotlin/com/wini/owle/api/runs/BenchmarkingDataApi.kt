package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.BenchmarkingDataDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class BenchmarkingDataApi(
    dll: DLL<Long, BenchmarkingDataDto>
) : LongIdEndpointImpl<BenchmarkingDataDto>(
    dll = dll,
    serializer = BenchmarkingDataDto.serializer(),
    validator = { _-> true }
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/benchmarking-data") {
                call.asyncResultList(getAll())
            }
            get("/api/benchmarking-data/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/benchmarking-data") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/benchmarking-data/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/benchmarking-data") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/benchmarking-data/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/benchmarking-data/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/benchmarking-data/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}