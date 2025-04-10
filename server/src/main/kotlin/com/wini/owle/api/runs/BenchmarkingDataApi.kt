package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.BenchmarkingDataDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall



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
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/benchmarking-data/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/benchmarking-data") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/benchmarking-data/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/benchmarking-data") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/benchmarking-data/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/benchmarking-data/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/benchmarking-data/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}