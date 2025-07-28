package com.wini.owle.api.workflows

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.validators.DtoValidator
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

class ToolApi(
    dll: DLL<Long, Tool>,
    validator: DtoValidator<Tool>
): LongIdEndpointImpl<Tool>(
    dll = dll,
    serializer = Tool.serializer(),
    validator = validator
), IToolApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/tool/{id}") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/tool") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/tool/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/tool") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/tool/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/tool/{id}") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) }
            }
            delete("/api/tool/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}