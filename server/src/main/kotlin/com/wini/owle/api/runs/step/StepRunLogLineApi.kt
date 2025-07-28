package com.wini.owle.api.runs.step

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.step.StepRunLogLine
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class StepRunLogLineApi(
    dll: DLL<Long, StepRunLogLine>,
    validator: DtoValidator<StepRunLogLine>
): LongIdEndpointImpl<StepRunLogLine>(
    dll = dll,
    serializer = StepRunLogLine.serializer(),
    validator = validator
), IStepRunLogLineApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/execution-log") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/execution-log/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/execution-log") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/execution-log/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/execution-log") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/execution-log/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/execution-log/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) }
            }
            delete("/api/execution-log/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}