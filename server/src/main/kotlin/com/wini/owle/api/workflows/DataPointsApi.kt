package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.DataPointDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator


class DataPointsApi(
    dll: DLL<Long, DataPointDto>,
    validator: DtoValidator<DataPointDto>
): LongIdEndpointImpl<DataPointDto>(
    dll = dll,
    serializer = DataPointDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/data-points") {
                call.execute(OwleApiCall.GetMany(DataPointDto::class))
            }
            get("/api/data-points/{id}") {
                call.execute(OwleApiCall.GetOne(DataPointDto::class))
            }
            post("/api/data-points") {
                call.execute(OwleApiCall.CreateOne(DataPointDto::class))
            }
            post("/api/data-points/batch") {
                call.execute(OwleApiCall.CreateMany(DataPointDto::class))
            }
            put("/api/data-points") {
                call.execute(OwleApiCall.UpdateOne(DataPointDto::class))
            }
            put("/api/data-points/batch") {
                call.execute(OwleApiCall.UpdateMany(DataPointDto::class))
            }
            delete("/api/data-points/{id}") {
                call.execute(OwleApiCall.DeleteOne(DataPointDto::class)) 
            }
            delete("/api/data-points/batch") {
                call.execute(OwleApiCall.DeleteMany(DataPointDto::class))
            }
        }
    }
}