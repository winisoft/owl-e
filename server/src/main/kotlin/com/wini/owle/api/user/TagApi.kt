package com.wini.owle.api.user

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.user.TagDll
import com.wini.owle.dto.user.TagDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class TagApi(
    dll: TagDll
) : LongIdEndpointImpl<TagDto>(
    dll = dll,
    serializer = TagDto.serializer(),
    validator = { _-> true }
) {

    private suspend fun ApplicationCall.asyncResultSingle(result: AsyncResult<TagDto>, isCreate: Boolean = false) {
        when (result) {
            is AsyncResult.Success -> respond(if (isCreate) HttpStatusCode.Created else HttpStatusCode.OK, result.data)
            is AsyncResult.Failure -> respond(result.error.httpStatusCode, result.error)
        }
    }
    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tag") {
                call.asyncResult(getAll())
            }
            get("/api/tag/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/tag") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/tag/batch") {
                call.asyncResult(createBatch(call, validator), isCreate = true)
            }
            put("/api/tag") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/tag/batch") {
                call.asyncResult(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/tag/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/tag/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}