package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.DataPointSave
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall

class RevisionApi(
    dll: DLL<Long, DataPointSave>,
    validator: DtoValidator<DataPointSave>
): LongIdEndpointImpl<DataPointSave>(
    dll = dll,
    serializer = DataPointSave.serializer(),
    validator = validator
), IRevisionApi {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/revision") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/revision/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/revision") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/revision/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/revision") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/revision/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/revision/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/revision/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}