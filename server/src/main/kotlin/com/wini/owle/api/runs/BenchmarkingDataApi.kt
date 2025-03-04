package com.wini.owle.api.runs

import com.wini.owle.api.GenericLongPkApi
import com.wini.owle.dll.runs.BenchmarkingDataDll
import com.wini.owle.dto.BenchmarkingDataDto
import io.ktor.server.routing.*

class BenchmarkingDataApi(dll: BenchmarkingDataDll) : GenericLongPkApi<BenchmarkingDataDto>(
    dll = dll,
    clazz = BenchmarkingDataDto::class,
    serializer = BenchmarkingDataDto.serializer()
)  {
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/benchmarking-data") { getAll(context.request.call) }
            get("/api/benchmarking-data/{id}") { getById(context.request.call) }
            post("/api/benchmarking-data") { create(context.request.call) }
            post("/api/benchmarking-data/batch") { createBatch(context.request.call) }
            put("/api/benchmarking-data") { update(context.request.call) }
            put("/api/benchmarking-data/batch") { updateBatch(context.request.call) }
            delete("/api/benchmarking-data/{id}") { super.delete(context.request.call) }
            delete("/api/benchmarking-data/batch") { deleteBatch(context.request.call) }
        }
    }
}