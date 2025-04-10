package com.wini.owle.api.typing

import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.dto.typing.TypeDeclarationDto
import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import com.wini.owle.api.OwleApiCall



class TypeDeclarationApi(
    dll: DLL<Long, TypeDeclarationDto>,
    validator: (TypeDeclarationDto) -> Boolean = { _-> true}
): LongIdEndpointImpl<TypeDeclarationDto>(
    dll = dll,
    serializer = TypeDeclarationDto.serializer(),
    validator = validator
)  {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-declaration") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/type-declaration/{id}") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/type-declaration") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/type-declaration/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/type-declaration") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/type-declaration/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/type-declaration/{id}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/type-declaration/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}