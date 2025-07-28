package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.Inquisition
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class InquisitionApi(
    dll: DLL<Long, Inquisition>,
    validator: DtoValidator<Inquisition>
): LongIdEndpointImpl<Inquisition>(
    dll = dll,
    serializer = Inquisition.serializer(),
    validator = validator
), IInquisitionApi  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/inquisition") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/inquisition/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/inquisition") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/inquisition/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/inquisition") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/inquisition/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/inquisition/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/inquisition/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}