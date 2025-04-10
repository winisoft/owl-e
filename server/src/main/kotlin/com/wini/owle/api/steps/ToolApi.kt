package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.ToolDto
import io.ktor.server.application.*
import io.ktor.server.routing.*import com.wini.owle.validators.DtoValidator


class ToolApi(
    dll: DLL<Long, ToolDto>,
    validator: DtoValidator<ToolDto>
): LongIdEndpointImpl<ToolDto>(
    dll = dll,
    serializer = ToolDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool") {
                call.execute(OwleApiCall.GetMany(ToolDto::class))
            }
            get("/api/tool/{id}") {
                call.execute(OwleApiCall.GetOne(ToolDto::class))
            }
            post("/api/tool") {
                call.execute(OwleApiCall.CreateOne(ToolDto::class))
            }
            post("/api/tool/batch") {
                call.execute(OwleApiCall.CreateMany(ToolDto::class))
            }
            put("/api/tool") {
                call.execute(OwleApiCall.UpdateOne(ToolDto::class))
            }
            put("/api/tool/batch") {
                call.execute(OwleApiCall.UpdateMany(ToolDto::class))
            }
            delete("/api/tool/{id}") {
                call.execute(OwleApiCall.DeleteOne(ToolDto::class)) 
            }
            delete("/api/tool/batch") {
                call.execute(OwleApiCall.DeleteMany(ToolDto::class))
            }
        }
    }
}