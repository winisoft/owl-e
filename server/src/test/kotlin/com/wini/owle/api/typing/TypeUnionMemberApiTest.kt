package com.wini.owle.api.typing

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeUnionMemberDto
import kotlinx.serialization.KSerializer
import org.jetbrains.exposed.dao.id.CompositeID

class TypeUnionMemberApiTest : BaseCompositePkApiTest<CompositeID, TypeUnionMemberDto, TypeUnionMemberApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TypeUnionMemberDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TypeUnionMemberDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TypeUnionMemberDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<CompositeID, TypeUnionMemberDto>): TypeUnionMemberApi {
        TODO("Not yet implemented")
    }

}