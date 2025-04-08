package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PackageDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class PackageApi(
    dll: DLL<Long, PackageDto>
): LongIdEndpointImpl<PackageDto>(
    dll = dll,
    serializer = PackageDto.serializer(),
    validator = { _-> true }
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/package") {
                call.asyncResultList(getAll())
            }
            get("/api/package/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/package") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/package/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/package") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/package/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/package/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/package/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }

}