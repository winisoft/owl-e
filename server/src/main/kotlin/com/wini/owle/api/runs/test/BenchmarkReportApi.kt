package com.wini.owle.api.runs.test

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.BenchmarkReport
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall


class BenchmarkReportApi(
    dll: DLL<Long, BenchmarkReport>,
    validator: DtoValidator<BenchmarkReport>
): LongIdEndpointImpl<BenchmarkReport>(
    dll = dll,
    serializer = BenchmarkReport.serializer(),
    validator = validator
), IBenchmarkReportApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/benchmarking-data") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/benchmarking-data/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/benchmarking-data") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/benchmarking-data/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/benchmarking-data") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/benchmarking-data/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/benchmarking-data/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) }
            }
            delete("/api/benchmarking-data/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}