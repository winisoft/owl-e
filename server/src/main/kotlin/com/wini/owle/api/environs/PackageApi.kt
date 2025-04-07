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
import kotlinx.serialization.serializer


class PackageApi(
    dll: PackageDll
): LongIdEndpointImpl<PackageDto>(
    dll = dll,
    serializer = PackageDto.serializer(),
    validator = { _-> true }
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<PackageDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/package") {
                call.asyncResult(getAll())
            }
            get("/api/package/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/package") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/package/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/package") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/package/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
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