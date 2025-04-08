package com.wini.owle.api.typing

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.entity.composteid.TypeUnionMemberIdFactory
import io.ktor.server.routing.*
import com.wini.owle.dll.DLL
import io.ktor.server.application.*
import io.ktor.util.*
import org.jetbrains.exposed.dao.id.CompositeID


class TypeUnionMemberApi(
    dll: DLL<CompositeID, TypeUnionMemberDto>
) : CompositeIdEndpointImpl<TypeUnionMemberDto>(
    dll = dll,
    serializer = TypeUnionMemberDto.serializer(),
    compositeIdFactory = TypeUnionMemberIdFactory,
    validator = { _-> true }
) {

    override fun ApplicationCall.readId(): CompositeID? {
        return compositeIdFactory.fromRequestParams(params = parameters.toMap())
    }

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-union-member") {
                call.asyncResultList(getAll())
            }
            get("/api/type-union-member/id?unionTypeDeclarationId={unionTypeDeclarationId}&memberTypeDeclarationId={memberTypeDeclarationId}") {
                call.asyncResultSingle(getById(call))
            }
            post("/api/type-union-member") {
                call.asyncResultSingle(create(call, validator), isCreate = true)
            }
            post("/api/type-union-member/batch") {
                call.asyncResultList(createBatch(call, validator), isCreate = true)
            }
            put("/api/type-union-member") {
                call.asyncResultSingle(update(call, validator))
            }
            put("/api/type-union-member/batch") {
                call.asyncResultList(updateBatch(call, validator))
            }
            delete("/api/type-union-member/id?transformationId={transformationId}&eligibleTypeDeclarationId={eligibleTypeDeclarationId}") {
                call.asyncResultUnit(delete(call))
            }
            delete("/api/type-union-member/batch") {
                call.asyncResultUnit(deleteBatch(call))
            }
        }
    }
}