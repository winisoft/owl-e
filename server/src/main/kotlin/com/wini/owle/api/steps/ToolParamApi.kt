package com.wini.owle.api.steps

import com.wini.owle.validators.DtoValidator
import com.wini.owle.dto.steps.ToolParam
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.api.steps.IToolParamApi

class ToolParamApi(
    dll: DLL<Long, ToolParam>,
    validator: DtoValidator<ToolParam>
): LongIdEndpointImpl<ToolParam>(
    dll = dll,
    serializer = ToolParam.serializer(),
    validator = validator
), IToolParamApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool-param") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/tool-param/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/tool-param") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/tool-param/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/tool-param") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/tool-param/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/tool-param/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/tool-param/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}