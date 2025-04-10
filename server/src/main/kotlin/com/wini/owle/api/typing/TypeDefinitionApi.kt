package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeDefinitionDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall



class TypeDefinitionApi(
    dll: DLL<Long, TypeDefinitionDto>,
    validator: (TypeDefinitionDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TypeDefinitionDto>(
    dll = dll,
    serializer = TypeDefinitionDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-definition") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/type-definition/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/type-definition") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/type-definition/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/type-definition") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/type-definition/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/type-definition/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/type-definition/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}