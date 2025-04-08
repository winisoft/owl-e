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
    dll: DLL<CompositeID, ProvisionDataPointDto>,
    validator: (ProvisionDataPointDto) -> Boolean = { _-> true}
): CompositeIdEndpointImpl<ProvisionDataPointDto>(
    dll = dll,
    serializer = ProvisionDataPointDto.serializer(),
    compositeIdFactory = ProvisionDataPointIdFactory,
    validator = validator
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
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/provision-datapoint") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/provision-datapoint/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/provision-datapoint") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/provision-datapoint/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/provision-datapoint/id?provisionId={provisionId}&dataPointId={dataPointId}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/provision-datapoint/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}