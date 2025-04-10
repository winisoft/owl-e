package com.wini.owle.api.typing

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.entity.composteid.TypeUnionMemberIdFactory
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import io.ktor.server.application.*
import io.ktor.util.*
import org.jetbrains.exposed.dao.id.CompositeID
import com.wini.owle.api.OwleApiCall
import com.wini.owle.async.AsyncResult


class TypeUnionMemberApi(
    dll: DLL<CompositeID, TypeUnionMemberDto>,
    validator: (TypeUnionMemberDto) -> Boolean = { _-> true}
): CompositeIdEndpointImpl<TypeUnionMemberDto>(
    dll = dll,
    serializer = TypeUnionMemberDto.serializer(),
    compositeIdFactory = TypeUnionMemberIdFactory,
    validator = validator
) {

    override fun ApplicationCall.readId(): AsyncResult<CompositeID> {
        return compositeIdFactory.fromRequestParams(params = parameters.toMap())
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-union-member") {
                call.execute(OwleApiCall.GetMany)
            }
            get("/api/type-union-member/id?unionTypeDeclarationId={unionTypeDeclarationId}&memberTypeDeclarationId={memberTypeDeclarationId}") {
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
            delete("/api/type-union-member/id?transformationId={transformationId}&eligibleTypeDeclarationId={eligibleTypeDeclarationId}") {
                call.execute(OwleApiCall.DeleteOne) 
            }
            delete("/api/type-union-member/batch") {
                call.execute(OwleApiCall.DeleteMany)
            }
        }
    }
}