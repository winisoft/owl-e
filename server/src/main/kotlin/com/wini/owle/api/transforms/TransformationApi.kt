package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.transforms.TransformationDll
import com.wini.owle.dto.transforms.TransformationDto
import com.wini.owle.dto.workflows.WorkflowDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import com.wini.owle.dll.DLL


class TransformationApi(
    dll: DLL<Long, TransformationDto>
) : LongIdEndpointImpl<TransformationDto>(
    dll = dll,
    serializer = TransformationDto.serializer()
)  {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation") {
                call.asyncResultMany(getAll())
            }
            get("/api/transformation/{id}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/transformation") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/transformation/batch") {
                call.asyncResultMany(createBatch(call, validator), isCreate = true)
            }
            put("/api/transformation") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/transformation/batch") {
                call.asyncResultMany(updateBatch(call, validator), isCreate = true)
            }
            delete("/api/transformation/{id}") {
                call.asyncResult(delete(call))
            }
            delete("/api/transformation/batch") {
                call.asyncResult(deleteBatch(call))
            }
        }
    }
}