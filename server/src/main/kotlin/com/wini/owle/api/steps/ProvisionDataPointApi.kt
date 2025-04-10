package com.wini.owle.api.steps

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.entity.composteid.ProvisionDataPointIdFactory
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import io.ktor.util.*
import org.jetbrains.exposed.dao.id.CompositeID
import com.wini.owle.api.OwleApiCall



class ProvisionDataPointApi(
    dll: DLL<CompositeID, ProvisionDataPointDto>,
    validator: (ProvisionDataPointDto) -> Boolean = { _-> true}
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
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/provision-datapoint/id?provisionId={provisionId}&dataPointId={dataPointId}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/provision-datapoint") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/provision-datapoint/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/provision-datapoint") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/provision-datapoint/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/provision-datapoint/id?provisionId={provisionId}&dataPointId={dataPointId}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/provision-datapoint/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}