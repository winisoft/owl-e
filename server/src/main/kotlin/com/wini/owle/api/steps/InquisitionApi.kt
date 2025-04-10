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
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/inquisition/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/inquisition") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/inquisition/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/inquisition") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/inquisition/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/inquisition/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/inquisition/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}