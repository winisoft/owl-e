package com.wini.owle.api.typing

import com.wini.owle.api.GenericCompositeIdApi
import com.wini.owle.dll.typing.TypeUnionMemberDll
import com.wini.owle.dto.TypeUnionMemberDto
import com.wini.owle.mapper.CompositeIdMap
import io.ktor.server.routing.*

class TypeUnionMemberApi(
    typeUnionMemberDll: TypeUnionMemberDll,
    compositeIdMap: CompositeIdMap<TypeUnionMemberDto>
) : GenericCompositeIdApi<TypeUnionMemberDto>(
    dll = typeUnionMemberDll,
    clazz = TypeUnionMemberDto::class,
    serializer = TypeUnionMemberDto.serializer(),
    compositeIdMapper = compositeIdMap
) {
    override fun register(routing: Routing) {
        routing.apply {
            routing.apply {
                get("/api/type-union-member") { getAll(context.request.call) }
                get("/api/type-union-member/{id}") { getById(context.request.call) }
                post("/api/type-union-member") { create(context.request.call) }
                post("/api/type-union-member/batch") { createBatch(context.request.call) }
                put("/api/type-union-member") { update(context.request.call) }
                put("/api/type-union-member/batch") { updateBatch(context.request.call) }
                delete("/api/type-union-member/{id}") { super.delete(context.request.call) }
                delete("/api/type-union-member/batch") { deleteBatch(context.request.call) }
            }
        }
    }
}