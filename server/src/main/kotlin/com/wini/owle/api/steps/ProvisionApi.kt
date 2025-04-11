package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ProvisionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator


class ProvisionApi(
    dll: DLL<Long, ProvisionDto>,
    validator: DtoValidator<ProvisionDto>
): LongIdEndpointImpl<ProvisionDto>(
    dll = dll,
    serializer = ProvisionDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/provision") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/provision/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/provision") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/provision/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/provision") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/provision/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/provision/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/provision/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}