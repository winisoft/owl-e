package com.wini.owle.api.runs

import com.wini.owle.services.runs.BenchmarkingDataService
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.BenchmarkingDataApi(service: BenchmarkingDataService) {

    routing {

        route("/runs/benchmarking-data") {
            get {

            }

            get("/{id}") {

            }

            post {

            }

            post("/batch") {

            }

            put {

            }

            put("/batch") {

            }

            delete("/{id}") {

            }

            delete("/batch") {

            }
        }

    }
}