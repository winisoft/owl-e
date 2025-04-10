package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.PhaseDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.api.OwleApiCall
import com.wini.owle.validators.DtoValidator


class PhaseApi(
    dll: DLL<Long, PhaseDto>,
    validator: DtoValidator<PhaseDto>
): LongIdEndpointImpl<PhaseDto>(
    dll = dll,
    serializer = PhaseDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/phase") {
                call.execute(OwleApiCall.GetMany(PhaseDto::class))
            }
            get("/api/phase/{id}") {
                call.execute(OwleApiCall.GetOne(PhaseDto::class))
            }
            post("/api/phase") {
                call.execute(OwleApiCall.CreateOne(PhaseDto::class))
            }
            post("/api/phase/batch") {
                call.execute(OwleApiCall.CreateMany(PhaseDto::class))
            }
            put("/api/phase") {
                call.execute(OwleApiCall.UpdateOne(PhaseDto::class))
            }
            put("/api/phase/batch") {
                call.execute(OwleApiCall.UpdateMany(PhaseDto::class))
            }
            delete("/api/phase/{id}") {
                call.execute(OwleApiCall.DeleteOne(PhaseDto::class)) 
            }
            delete("/api/phase/batch") {
                call.execute(OwleApiCall.DeleteMany(PhaseDto::class))
            }
        }
    }
}