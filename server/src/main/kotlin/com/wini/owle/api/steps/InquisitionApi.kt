package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.InquisitionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator


class InquisitionApi(
    dll: DLL<Long, InquisitionDto>,
    validator: DtoValidator<InquisitionDto>
): LongIdEndpointImpl<InquisitionDto>(
    dll = dll,
    serializer = InquisitionDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/inquisition") {
                call.execute(OwleApiCall.GetMany(InquisitionDto::class))
            }
            get("/api/inquisition/{id}") {
                call.execute(OwleApiCall.GetOne(InquisitionDto::class))
            }
            post("/api/inquisition") {
                call.execute(OwleApiCall.CreateOne(InquisitionDto::class))
            }
            post("/api/inquisition/batch") {
                call.execute(OwleApiCall.CreateMany(InquisitionDto::class))
            }
            put("/api/inquisition") {
                call.execute(OwleApiCall.UpdateOne(InquisitionDto::class))
            }
            put("/api/inquisition/batch") {
                call.execute(OwleApiCall.UpdateMany(InquisitionDto::class))
            }
            delete("/api/inquisition/{id}") {
                call.execute(OwleApiCall.DeleteOne(InquisitionDto::class)) 
            }
            delete("/api/inquisition/batch") {
                call.execute(OwleApiCall.DeleteMany(InquisitionDto::class))
            }
        }
    }
}