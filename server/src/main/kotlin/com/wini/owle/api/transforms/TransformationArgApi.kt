package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.transforms.TransformationArgDll
import com.wini.owle.dto.transforms.TransformationArgDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer

class TransformationArgApi(
    dll: TransformationArgDll
) : LongIdEndpointImpl<TransformationArgDto>(
    dll = dll,
    serializer = TransformationArgDto.serializer()
)  {


    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation-arg") {
                call.asyncResultMany(getAll())
            }
            get("/api/transformation-arg/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/transformation-arg") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/transformation-arg/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/transformation-arg") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/transformation-arg/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/transformation-arg/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/transformation-arg/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}