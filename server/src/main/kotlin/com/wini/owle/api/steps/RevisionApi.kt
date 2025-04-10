package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.steps.RevisionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.validators.DtoValidator


class RevisionApi(
    dll: DLL<Long, RevisionDto>,
    validator: DtoValidator<RevisionDto>
): LongIdEndpointImpl<RevisionDto>(
    dll = dll,
    serializer = RevisionDto.serializer(),
    validator = validator
) {



    override fun register(routing: Routing) {
        routing.apply {
            get("/api/revision") {
                call.execute(OwleApiCall.GetMany(RevisionDto::class))
            }
            get("/api/revision/{id}") {
                call.execute(OwleApiCall.GetOne(RevisionDto::class))
            }
            post("/api/revision") {
                call.execute(OwleApiCall.CreateOne(RevisionDto::class))
            }
            post("/api/revision/batch") {
                call.execute(OwleApiCall.CreateMany(RevisionDto::class))
            }
            put("/api/revision") {
                call.execute(OwleApiCall.UpdateOne(RevisionDto::class))
            }
            put("/api/revision/batch") {
                call.execute(OwleApiCall.UpdateMany(RevisionDto::class))
            }
            delete("/api/revision/{id}") {
                call.execute(OwleApiCall.DeleteOne(RevisionDto::class)) 
            }
            delete("/api/revision/batch") {
                call.execute(OwleApiCall.DeleteMany(RevisionDto::class))
            }
        }
    }
}