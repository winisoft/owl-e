package com.wini.owle.api.typing

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.typing.TypeUnionMemberDll
import com.wini.owle.dto.typing.TypeUnionMemberDto
import com.wini.owle.mapper.CompositeIdFactory
import com.wini.owle.mapper.TypeUnionMemberIdFactory
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json


class TypeUnionMemberApi(
    typeUnionMemberDll: TypeUnionMemberDll
) : CompositeIdEndpointImpl<TypeUnionMemberDto>(
    dll = typeUnionMemberDll,
    serializer = TypeUnionMemberDto.serializer(),
    compositeIdFactory = TypeUnionMemberIdFactory,
    validator = { _-> true }
) {

    override fun register(routing: Routing) {
        routing.apply {
            routing.apply {
                get("/api/type-union-member") { getAll() }
                get("/api/type-union-member/{id}") { getById(context.request.call) }
                post("/api/type-union-member") { create(context.request.call) }
                post("/api/type-union-member/batch") { createBatch(context.request.call) }
                put("/api/type-union-member") { update(context.request.call) }
                put("/api/type-union-member/batch") { updateBatch(context.request.call) }
                delete("/api/type-union-member/{id}") { delete(context.request.call) }
                delete("/api/type-union-member/batch") { deleteBatch(context.request.call) }
            }
        }
    }
}