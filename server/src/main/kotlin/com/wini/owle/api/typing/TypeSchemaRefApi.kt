package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeSchemaRefDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall



class TypeSchemaRefApi(
    dll: DLL<Long, TypeSchemaRefDto>,
    validator: (TypeSchemaRefDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TypeSchemaRefDto>(
    dll = dll,
    serializer = TypeSchemaRefDto.serializer(),
    validator = validator
) {


    
    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-schema-ref") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/type-schema-ref/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/type-schema-ref") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/type-schema-ref/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/type-schema-ref") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/type-schema-ref/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/type-schema-ref/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/type-schema-ref/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}