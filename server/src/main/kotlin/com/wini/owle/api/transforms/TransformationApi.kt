package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.TransformationDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall



class TransformationApi(
    dll: DLL<Long, TransformationDto>,
    validator: (TransformationDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TransformationDto>(
    dll = dll,
    serializer = TransformationDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/transformation/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/transformation") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/transformation/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/transformation") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/transformation/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/transformation/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/transformation/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}