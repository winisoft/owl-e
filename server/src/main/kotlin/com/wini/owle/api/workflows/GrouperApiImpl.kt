package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Phase
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.ULongIdEndpointImpl

class GrouperApiImpl(
    dll: DLL<ULong, Phase>,
    validator: DtoValidator<Phase>,
    override val endpointPath: String
): ULongIdEndpointImpl<Phase>(
    dll = dll,
    serializer = Phase.serializer(),
    validator = validator
), IPhaseApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/phase") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/phase/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/phase") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/phase/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/phase") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/phase/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/phase/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/phase/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}