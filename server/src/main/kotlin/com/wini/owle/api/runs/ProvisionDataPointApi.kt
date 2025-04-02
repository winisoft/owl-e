package com.wini.owle.api.runs

import com.wini.owle.api.GenericCompositeIdApi
import com.wini.owle.dll.steps.ProvisionDataPointDll
import com.wini.owle.dto.steps.ProvisionDataPointDto
import com.wini.owle.mapper.CompositeIdMap
import io.ktor.server.routing.*

class ProvisionDataPointApi(
    provisionDataPointDll: ProvisionDataPointDll,
    compositeIdMap: CompositeIdMap<ProvisionDataPointDto>
) : GenericCompositeIdApi<ProvisionDataPointDto>(
    dll = provisionDataPointDll,
    clazz = ProvisionDataPointDto::class,
    serializer = ProvisionDataPointDto.serializer(),
    compositeIdMapper = compositeIdMap
) {
    override fun register(routing: Routing) {
        routing.apply {
            routing.apply {
                get("/api/provision-datapoint") { getAll(context.request.call) }
                get("/api/provision-datapoint/{id}") { getById(context.request.call) }
                post("/api/provision-datapoint") { create(context.request.call) }
                post("/api/provision-datapoint/batch") { createBatch(context.request.call) }
                put("/api/provision-datapoint") { update(context.request.call) }
                put("/api/provision-datapoint/batch") { updateBatch(context.request.call) }
                delete("/api/provision-datapoint/{id}") { super.delete(context.request.call) }
                delete("/api/provision-datapoint/batch") { deleteBatch(context.request.call) }
            }
        }
    }
}