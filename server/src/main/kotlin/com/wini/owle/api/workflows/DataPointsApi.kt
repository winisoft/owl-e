package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.workflows.DataPointDll
import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class DataPointsApi(
    dll: DataPointDll
) : LongIdEndpointImpl<DataPointDto>(
    dll = dll,
    serializer = DataPointDto.serializer(),
    validator = { _-> true }
) {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<DataPointDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/data-points") {
                call.asyncResult(getAll())
            }
            get("/api/data-points/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/data-points") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/data-points/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/data-points") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/data-points/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/data-points/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/data-points/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}