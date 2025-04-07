package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.environs.PackageRepoDll
import com.wini.owle.dto.environs.PackageRepoDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

class PackageRepoApi(
    dll: PackageRepoDll
): LongIdEndpointImpl<PackageRepoDto>(
    dll = dll,
    serializer = PackageRepoDto.serializer(),
    validator = { _-> true }
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/package-repo") {
                call.asyncResultMany(getAll())
            }
            get("/api/package-repo/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/package-repo") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/package-repo/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/package-repo") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/package-repo/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/package-repo/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/package-repo/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}