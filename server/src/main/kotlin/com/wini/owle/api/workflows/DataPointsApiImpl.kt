package com.wini.owle.api.workflows

import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.DataPoint
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.ULongIdEndpointImpl
import com.wini.owle.validators.DtoValidator


class DataPointsApiImpl(
    dll: DLL<ULong, DataPoint>,
    validator: DtoValidator<DataPoint>,
    override val endpointPath: String
): ULongIdEndpointImpl<DataPoint>(
    dll = dll,
    serializer = DataPoint.serializer(),
    endpointPath = endpointPath,
    validator = validator
), DataPointsApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/data-points") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/data-points/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/data-points") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/data-points/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/data-points") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/data-points/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/data-points/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/data-points/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}