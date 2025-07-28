package com.wini.owle.api.typing

import com.wini.owle.api.CompositeIdEndpointImpl
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.validators.DtoValidator
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.id.CompositeID
import com.wini.owle.api.LongIdEndpointImpl
import com.wini.owle.api.OwleApiCall


class TypeUnionMemberApi(
    dll: DLL<CompositeID, TypeUnionMember>,
    validator: DtoValidator<TypeUnionMember>,
    compositeIdFactory: CompositeIdFactory<TypeUnionMember>
): CompositeIdEndpointImpl<TypeUnionMember>(
    dll = dll,
    serializer = TypeUnionMember.serializer(),
    compositeIdFactory = compositeIdFactory,
    validator = validator
), ITypeUnionMemberApi {

    override fun register(routing: Routing) {
        routing.apply {
            get("/api/type-union-member") {
                getMany { call.execute(OwleApiCall.GetMany) }
            }
            get("/api/type-union-member/id") {
                getOne { call.execute(OwleApiCall.GetOne) }
            }
            post("/api/type-union-member") {
                postMany { call.execute(OwleApiCall.CreateOne) }
            }
            post("/api/type-union-member/batch") {
                postMany { call.execute(OwleApiCall.CreateMany) }
            }
            put("/api/type-union-member") {
                putOne { call.execute(OwleApiCall.UpdateOne) }
            }
            put("/api/type-union-member/batch") {
                putMany { call.execute(OwleApiCall.UpdateMany) }
            }
            delete("/api/type-union-member/id") {
                deleteOne { call.execute(OwleApiCall.DeleteOne) } 
            }
            delete("/api/type-union-member/batch") {
                deleteMany { call.execute(OwleApiCall.DeleteMany) }
            }
        }
    }
}