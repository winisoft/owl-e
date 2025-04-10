package com.wini.owle.api.steps

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.ProvisionDataPointIdFactory
import com.wini.owle.validators.DtoValidator
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.id.CompositeID


class ProvisionDataPointApi(
    dll: DLL<CompositeID, ProvisionDataPointDto>,
    validator: DtoValidator<ProvisionDataPointDto>,
    compositeIdFactory: CompositeIdFactory<ProvisionDataPointDto>
): CompositeIdEndpointImpl<ProvisionDataPointDto>(
    dll = dll,
    serializer = ProvisionDataPointDto.serializer(),
    compositeIdFactory = compositeIdFactory,
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/provision-datapoint") {
                call.execute(OwleApiCall.GetMany(ProvisionDataPointDto::class))
            }
            get("/api/provision-datapoint/id") {
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
            delete("/api/provision-datapoint/id") {
                call.execute(OwleApiCall.DeleteOne(ProvisionDataPointDto::class)) 
            }
            delete("/api/provision-datapoint/batch") {
                call.execute(OwleApiCall.DeleteMany(ProvisionDataPointDto::class))
            }
        }
    }
}