package com.wini.owle.api.steps

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.workflows.ToolDto
import io.ktor.server.application.*
import io.ktor.server.routing.*

class ToolApi(
    dll: DLL<Long, ToolDto>,
    validator: (ToolDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<ToolDto>(
    dll = dll,
    serializer = ToolDto.serializer(),
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/tool") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/tool/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/tool") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/tool/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/tool") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/tool/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/tool/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/tool/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}