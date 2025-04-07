// server/src/test/kotlin/com/wini/owle/api/typing/TypeUnionMemberApiTest.kt
package com.wini.owle.api.typing

import com.wini.owle.api.BaseCompositePkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeUnionMemberDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import org.jetbrains.exposed.dao.id.CompositeID

open class TypeUnionMemberApiTest : BaseCompositePkApiTest<TypeUnionMemberDto, TypeUnionMemberApi>() {
    override val route: String = "/api/type-union-member"

    override fun getDto(): TypeUnionMemberDto = DtoTestInstanceFactory.typeUnionMemberDto()

    override val serializer: KSerializer<TypeUnionMemberDto> = TypeUnionMemberDto.serializer()
    
    override val listSerializer: KSerializer<List<TypeUnionMemberDto>> = ListSerializer(TypeUnionMemberDto.serializer())

    override fun getApiInstance(dll: DLL<CompositeID, TypeUnionMemberDto>): TypeUnionMemberApi = TypeUnionMemberApi(dll)
}