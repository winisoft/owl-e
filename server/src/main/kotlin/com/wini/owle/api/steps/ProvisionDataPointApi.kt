package com.wini.owle.api.steps

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.ProvisionDataPoint
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.validators.DtoValidator
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.id.CompositeID


class ProvisionDataPointApi(
    dll: DLL<CompositeID, ProvisionDataPoint>,
    validator: DtoValidator<ProvisionDataPoint>,
    compositeIdFactory: CompositeIdFactory<ProvisionDataPoint>
): CompositeIdEndpointImpl<ProvisionDataPoint>(
    dll = dll,
    serializer = ProvisionDataPoint.serializer(),
    compositeIdFactory = compositeIdFactory,
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/provision-datapoint") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/provision-datapoint/id") {
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
            delete("/api/provision-datapoint/id") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/provision-datapoint/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}