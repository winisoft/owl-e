package com.wini.owle.api.environs

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.environs.PackageDll
import com.wini.owle.dto.environs.PackageDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.serializer


class PackageApi(
    dll: PackageDll
): LongIdEndpointImpl<PackageDto>(
    dll = dll,
    serializer = PackageDto.serializer(),
    validator = { _-> true }
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/package") {
                call.asyncResultMany(getAll())
            }
            get("/api/package/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/package") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/package/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/package") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/package/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/package/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/package/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }

}