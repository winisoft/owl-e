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
import com.wini.owle.validators.DtoValidator


class TypeUnionMemberApi(
    dll: DLL<CompositeID, TypeUnionMemberDto>,
    validator: DtoValidator<TypeUnionMemberDto>
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
                call.execute(OwleApiCall.GetMany(TypeUnionMemberDto::class))
            }
            get("/api/type-union-member/id?unionTypeDeclarationId={unionTypeDeclarationId}&memberTypeDeclarationId={memberTypeDeclarationId}") {
                call.execute(OwleApiCall.GetOne(TypeUnionMemberDto::class))
            }
            post("/api/type-union-member") {
                call.execute(OwleApiCall.CreateOne(TypeUnionMemberDto::class))
            }
            post("/api/type-union-member/batch") {
                call.execute(OwleApiCall.CreateMany(TypeUnionMemberDto::class))
            }
            put("/api/type-union-member") {
                call.execute(OwleApiCall.UpdateOne(TypeUnionMemberDto::class))
            }
            put("/api/type-union-member/batch") {
                call.execute(OwleApiCall.UpdateMany(TypeUnionMemberDto::class))
            }
            delete("/api/type-union-member/id?transformationId={transformationId}&eligibleTypeDeclarationId={eligibleTypeDeclarationId}") {
                call.execute(OwleApiCall.DeleteOne(TypeUnionMemberDto::class)) 
            }
            delete("/api/type-union-member/batch") {
                call.execute(OwleApiCall.DeleteMany(TypeUnionMemberDto::class))
            }
        }
    }
}