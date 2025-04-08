package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.PackageDto
import io.ktor.server.application.*
import io.ktor.server.routing.*


class PackageApi(
    dll: DLL<Long, PackageDto>,
    validator: (PackageDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<PackageDto>(
    dll = dll,
    serializer = PackageDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/package") {
                call.asyncResultList(getAll())
            }
            get("/api/package/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/package") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/package/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/package") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/package/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/package/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/package/batch") {
                call.apply { asyncResultUnit(deleteBatch()) } 
            }
        }
    }

}