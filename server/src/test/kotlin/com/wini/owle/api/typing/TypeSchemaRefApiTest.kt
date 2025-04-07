package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeSchemaRefDto
import kotlinx.serialization.KSerializer

class TypeSchemaRefApiTest : BaseLongPkApiTest<Long, TypeSchemaRefDto, TypeSchemaRefApi>() {
    override val route: String
        get() = TODO("Not yet implemented")

    override fun getDto(): TypeSchemaRefDto {
        TODO("Not yet implemented")
    }

    override val serializer: KSerializer<TypeSchemaRefDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TypeSchemaRefDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TypeSchemaRefDto>): TypeSchemaRefApi {
        TODO("Not yet implemented")
    }

}