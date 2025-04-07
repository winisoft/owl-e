package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeDefinitionDto
import kotlinx.serialization.KSerializer


class TypeDefinitionApiTest : BaseLongPkApiTest<Long, TypeDefinitionDto, TypeDefinitionApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TypeDefinitionDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TypeDefinitionDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TypeDefinitionDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TypeDefinitionDto>): TypeDefinitionApi {
        TODO("Not yet implemented")
    }

}