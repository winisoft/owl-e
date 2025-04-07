package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeParameterDto
import kotlinx.serialization.KSerializer


class TypeParameterApiTest : BaseLongPkApiTest<Long, TypeParameterDto, TypeParameterApi>() {
    override val route: String
        get() = "/api/type-parameter"

    override fun getDto(): TypeParameterDto = DtoTestInstanceFactory.typeParameterDto()

    override val serializer: KSerializer<TypeParameterDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TypeParameterDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TypeParameterDto>): TypeParameterApi {
        TODO("Not yet implemented")
    }

}