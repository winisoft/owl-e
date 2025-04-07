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

class VersionSpecApi(
    dll: VersionSpecDll
) : LongIdEndpointImpl<VersionSpecDto>(
    dll = dll,
    serializer = VersionSpecDto.serializer(),
    validator = { _-> true }
)  {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<VersionSpecDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/version-spec") {
                call.asyncResult(getAll())
            }
            get("/api/version-spec/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/version-spec") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/version-spec/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/version-spec") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/version-spec/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
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