package com.wini.owle.api.typing

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.api.OwleApiCall
import com.wini.owle.dll.DLL
import com.wini.owle.dto.environs.ToolPackageDependencyDto
import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.TypeUnionMemberIdFactory
import com.wini.owle.validators.DtoValidator
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.id.CompositeID


class TypeUnionMemberApi(
    dll: DLL<CompositeID, TypeUnionMemberDto>,
    validator: DtoValidator<TypeUnionMemberDto>,
    compositeIdFactory: CompositeIdFactory<TypeUnionMemberDto>
): CompositeIdEndpointImpl<TypeUnionMemberDto>(
    dll = dll,
    serializer = TypeUnionMemberDto.serializer(),
    compositeIdFactory = compositeIdFactory,
    validator = validator
) {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-union-member") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/type-union-member/id") {
                call.execute(OwleApiCall.GetOne)
            }
            post("/api/type-union-member") {
                call.execute(OwleApiCall.CreateOne)
            }
            post("/api/type-union-member/batch") {
                call.execute(OwleApiCall.CreateMany)
            }
            put("/api/type-union-member") {
                call.execute(OwleApiCall.UpdateOne)
            }
            put("/api/type-union-member/batch") {
                call.execute(OwleApiCall.UpdateMany)
            }
            delete("/api/type-union-member/id") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/type-union-member/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}