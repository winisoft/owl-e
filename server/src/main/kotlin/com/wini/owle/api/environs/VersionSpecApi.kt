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
                call.asyncResultMany(getAll())
            }
            get("/api/version-spec/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/version-spec") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/version-spec/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/version-spec") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/version-spec/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/version-spec/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/version-spec/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}