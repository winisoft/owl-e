package com.wini.owle.api.steps

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.entity.composteid.ProvisionDataPointIdFactory
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import io.ktor.util.*
import org.jetbrains.exposed.dao.id.CompositeID


class ProvisionDataPointApi(
    dll: DLL<CompositeID, ProvisionDataPointDto>
) : CompositeIdEndpointImpl<ProvisionDataPointDto>(
    dll = dll,
    serializer = ProvisionDataPointDto.serializer(),
    compositeIdFactory = ProvisionDataPointIdFactory,
    validator = { _-> true }
) {

    override fun ApplicationCall.readId(): CompositeID? {
        return compositeIdFactory.fromRequestParams(params = parameters.toMap())
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/provision-datapoint") {
                call.asyncResultList(getAll())
            }
            get("/api/provision-datapoint/id?provisionId={provisionId}&dataPointId={dataPointId}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/provision-datapoint") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/provision-datapoint/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/provision-datapoint") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/provision-datapoint/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/provision-datapoint/id?provisionId={provisionId}&dataPointId={dataPointId}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/provision-datapoint/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}