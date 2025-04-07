package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.environs.PackageRepoDll
import com.wini.owle.dll.environs.VersionSpecDll
import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.dto.environs.VersionSpecDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

class VersionSpecApi(
    dll: VersionSpecDll
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