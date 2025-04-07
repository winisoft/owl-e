package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeBoundsDto
import kotlinx.serialization.KSerializer


class TypeBoundsApiTest : BaseLongPkApiTest<Long, TypeBoundsDto, TypeBoundsApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TypeBoundsDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TypeBoundsDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TypeBoundsDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TypeBoundsDto>): TypeBoundsApi {
        TODO("Not yet implemented")
    }

}