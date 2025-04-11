package com.wini.owle.api.runs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.runs.ExecutionLogDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator


class ExecutionLogApi(
    dll: DLL<Long, ExecutionLogDto>,
    validator: DtoValidator<ExecutionLogDto>
): LongIdEndpointImpl<ExecutionLogDto>(
    dll = dll,
    serializer = ExecutionLogDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/execution-log") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/execution-log/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/execution-log") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/execution-log/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/execution-log") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/execution-log/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/execution-log/{id}") {
                call.execute(OwleApiCall.DeleteOne)
            }
            delete("/api/execution-log/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}