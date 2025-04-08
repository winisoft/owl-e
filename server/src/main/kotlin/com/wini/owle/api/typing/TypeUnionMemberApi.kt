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
    dll: DLL<CompositeID, TypeUnionMemberDto>,
    validator: (TypeUnionMemberDto) -> Boolean = { _-> true}
): CompositeIdEndpointImpl<TypeUnionMemberDto>(
    dll = dll,
    serializer = TypeUnionMemberDto.serializer(),
    compositeIdFactory = TypeUnionMemberIdFactory,
    validator = validator
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
                call.apply { asyncResultSingle(getById()) }
            }
            post("/api/type-union-member") {
                call.apply { asyncResultSingle(create(validator), isCreate = true) }
            }
            post("/api/type-union-member/batch") {
                call.apply { asyncResultList(createBatch(validator), isCreate = true) }
            }
            put("/api/type-union-member") {
                call.apply { asyncResultSingle(update(validator)) }
            }
            put("/api/type-union-member/batch") {
                call.apply { asyncResultList(updateBatch(validator)) }
            }
            delete("/api/type-union-member/id?transformationId={transformationId}&eligibleTypeDeclarationId={eligibleTypeDeclarationId}") {
                call.apply { asyncResultUnit(delete()) }
            }
            delete("/api/type-union-member/batch") {
                call.apply { asyncResultUnit(deleteBatch()) }
            }
        }
    }
}