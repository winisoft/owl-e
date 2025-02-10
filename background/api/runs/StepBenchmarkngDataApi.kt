package com.wini.owle.api.runs


import com.wini.owle.services.runs.StepBenchmarkingDataService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.StepBenchmarkingDataApi(service: StepBenchmarkingDataService) {

    routing {

        route("/runs/step-benchmarking-data") {

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
