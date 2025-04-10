package com.wini.owle.api.transforms

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.transforms.TransformationArgDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall



class TransformationArgApi(
    dll: DLL<Long, TransformationArgDto>,
    validator: (TransformationArgDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TransformationArgDto>(
    dll = dll,
    serializer = TransformationArgDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/transformation-arg") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/transformation-arg/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/transformation-arg") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/transformation-arg/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/transformation-arg") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/transformation-arg/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/transformation-arg/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/transformation-arg/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}