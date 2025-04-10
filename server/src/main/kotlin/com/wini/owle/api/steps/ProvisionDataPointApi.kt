package com.wini.owle.api.steps

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.async.AsyncResult
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import io.ktor.util.*
import org.jetbrains.exposed.dao.id.CompositeID
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.entity.composteid.ProvisionDataPointIdFactory

import com.wini.owle.validators.DtoValidator


class ProvisionDataPointApi(
    dll: DLL<CompositeID, ProvisionDataPointDto>,
    validator: DtoValidator<ProvisionDataPointDto>
): CompositeIdEndpointImpl<ProvisionDataPointDto>(
    dll = dll,
    serializer = ProvisionDataPointDto.serializer(),
    compositeIdFactory = ProvisionDataPointIdFactory,
    validator = validator
) {

    override fun ApplicationCall.readId(): AsyncResult<CompositeID> {
        return compositeIdFactory.fromRequestParams(params = request.queryParameters.toMap())
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/provision-datapoint") {
                call.execute(OwleApiCall.GetMany(ProvisionDataPointDto::class))
            }
            get("/api/provision-datapoint/id?provisionId={provisionId}&dataPointId={dataPointId}") {
                call.execute(OwleApiCall.GetOne(ProvisionDataPointDto::class))
            }
            post("/api/provision-datapoint") {
                call.execute(OwleApiCall.CreateOne(ProvisionDataPointDto::class))
            }
            post("/api/provision-datapoint/batch") {
                call.execute(OwleApiCall.CreateMany(ProvisionDataPointDto::class))
            }
            put("/api/provision-datapoint") {
                call.execute(OwleApiCall.UpdateOne(ProvisionDataPointDto::class))
            }
            put("/api/provision-datapoint/batch") {
                call.execute(OwleApiCall.UpdateMany(ProvisionDataPointDto::class))
            }
            delete("/api/provision-datapoint/id?provisionId={provisionId}&dataPointId={dataPointId}") {
                call.execute(OwleApiCall.DeleteOne(ProvisionDataPointDto::class)) 
            }
            delete("/api/provision-datapoint/batch") {
                call.execute(OwleApiCall.DeleteMany(ProvisionDataPointDto::class))
            }
        }
    }
}