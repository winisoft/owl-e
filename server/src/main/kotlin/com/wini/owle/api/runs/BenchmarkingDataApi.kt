package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.BenchmarkingDataDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class BenchmarkingDataApi(
    dll: DLL<Long, BenchmarkingDataDto>,
    validator: (BenchmarkingDataDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<BenchmarkingDataDto>(
    dll = dll,
    serializer = BenchmarkingDataDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/benchmarking-data") {
                call.asyncResultList(getAll())
            }
            get("/api/benchmarking-data/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/benchmarking-data") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/benchmarking-data/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/benchmarking-data") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/benchmarking-data/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/benchmarking-data/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/benchmarking-data/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}