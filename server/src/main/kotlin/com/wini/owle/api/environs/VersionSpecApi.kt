package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.environs.VersionSpecDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL

class VersionSpecApi(
    dll: DLL<Long, VersionSpecDto>
) : LongIdEndpointImpl<VersionSpecDto>(
    dll = dll,
    serializer = VersionSpecDto.serializer(),
    validator = { _-> true }
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/version-spec") {
                call.asyncResultList(getAll())
            }
            get("/api/version-spec/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/version-spec") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/version-spec/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/version-spec") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/version-spec/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/version-spec/{id}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/version-spec/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}