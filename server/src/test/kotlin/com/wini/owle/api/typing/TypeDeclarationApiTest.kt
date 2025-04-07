package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeDeclarationDto
import kotlinx.serialization.KSerializer


class TypeDeclarationApiTest : BaseLongPkApiTest<Long, TypeDeclarationDto, TypeDeclarationApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TypeDeclarationDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TypeDeclarationDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TypeDeclarationDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TypeDeclarationDto>): TypeDeclarationApi {
        TODO("Not yet implemented")
    }

}