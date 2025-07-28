package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.Provision
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class ProvisionApi(
    dll: DLL<Long, Provision>,
    validator: DtoValidator<Provision>
): LongIdEndpointImpl<Provision>(
    dll = dll,
    serializer = Provision.serializer(),
    validator = validator
), IProvisionApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/provision") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/provision/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/provision") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/provision/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/provision") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/provision/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/provision/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/provision/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}