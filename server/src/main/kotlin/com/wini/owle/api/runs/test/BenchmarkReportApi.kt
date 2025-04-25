package com.wini.owle.api.runs.test

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.BenchmarkReport
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator


class BenchmarkReportApi(
    dll: DLL<Long, BenchmarkReport>,
    validator: DtoValidator<BenchmarkReport>
): LongIdEndpointImpl<BenchmarkReport>(
    dll = dll,
    serializer = BenchmarkReport.serializer(),
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