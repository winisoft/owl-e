package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.StepDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator


class StepApi(
    dll: DLL<Long, StepDto>,
    validator: DtoValidator<StepDto>
): LongIdEndpointImpl<StepDto>(
    dll = dll,
    serializer = StepDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/step") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/step/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/step") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/step/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/step") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/step/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/step/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/step/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}