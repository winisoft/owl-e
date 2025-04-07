package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.transforms.AppliedTransformDll
import com.wini.owle.dto.transforms.AppliedTransformDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class AppliedTransformApi(
    dll: AppliedTransformDll
) : LongIdEndpointImpl<AppliedTransformDto>(
    dll = dll,
    serializer = AppliedTransformDto.serializer()
)  {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/applied-transform") {
                call.asyncResultMany(getAll())
            }
            get("/api/applied-transform/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/applied-transform") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/applied-transform/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/applied-transform") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/applied-transform/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/applied-transform/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/applied-transform/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}