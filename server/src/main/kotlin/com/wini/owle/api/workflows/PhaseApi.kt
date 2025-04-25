package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Phase
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator


class PhaseApi(
    dll: DLL<Long, Phase>,
    validator: DtoValidator<Phase>
): LongIdEndpointImpl<Phase>(
    dll = dll,
    serializer = Phase.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/phase") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/phase/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/phase") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/phase/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/phase") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/phase/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/phase/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/phase/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}