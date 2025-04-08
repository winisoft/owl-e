package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.environs.VersionSpecDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL

class VersionSpecApi(
    dll: DLL<Long, VersionSpecDto>,
    validator: (VersionSpecDto) -> Boolean = { _-> true}
) : LongIdEndpointImpl<VersionSpecDto>(
    dll = dll,
    serializer = VersionSpecDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/version-spec") {
                call.asyncResultList(getAll())
            }
            get("/api/version-spec/{id}") {
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/version-spec") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/version-spec/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/version-spec") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/version-spec/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/version-spec/{id}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/version-spec/batch") {
                call.apply { asyncResultUnit(deleteBatch()) } 
            }
        }
    }
}