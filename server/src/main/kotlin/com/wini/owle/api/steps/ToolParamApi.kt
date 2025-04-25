package com.wini.owle.api.steps

import com.wini.owle.validators.DtoValidator
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dto.workflows.ToolParam
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL


class ToolParamApi(
    dll: DLL<Long, ToolParam>,
    validator: DtoValidator<ToolParam>
): LongIdEndpointImpl<ToolParam>(
    dll = dll,
    serializer = ToolParam.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-param") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/tool-param/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/tool-param") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/tool-param/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/tool-param") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/tool-param/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/tool-param/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/tool-param/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}