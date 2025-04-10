package com.wini.owle.api.steps

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dto.workflows.ToolParamDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class ToolParamApi(
    dll: DLL<Long, ToolParamDto>,
    validator: DtoValidator<ToolParamDto>
): LongIdEndpointImpl<ToolParamDto>(
    dll = dll,
    serializer = ToolParamDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-param") {
                call.execute(OwleApiCall.GetMany(ToolParamDto::class))
            }
            get("/api/tool-param/{id}") {
                call.execute(OwleApiCall.GetOne(ToolParamDto::class))
            }
            post("/api/tool-param") {
                call.execute(OwleApiCall.CreateOne(ToolParamDto::class))
            }
            post("/api/tool-param/batch") {
                call.execute(OwleApiCall.CreateMany(ToolParamDto::class))
            }
            put("/api/tool-param") {
                call.execute(OwleApiCall.UpdateOne(ToolParamDto::class))
            }
            put("/api/tool-param/batch") {
                call.execute(OwleApiCall.UpdateMany(ToolParamDto::class))
            }
            delete("/api/tool-param/{id}") {
                call.execute(OwleApiCall.DeleteOne(ToolParamDto::class)) 
            }
            delete("/api/tool-param/batch") {
                call.execute(OwleApiCall.DeleteMany(ToolParamDto::class))
            }
        }
    }
}