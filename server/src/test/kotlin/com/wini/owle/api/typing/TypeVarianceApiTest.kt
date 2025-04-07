package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeVarianceDto
import kotlinx.serialization.KSerializer


class TypeVarianceApiTest : BaseLongPkApiTest<Long, TypeVarianceDto, TypeVarianceApi>() {

    override val route: String
        get() = "/api/type-variance"

    override fun getDto(): TypeVarianceDto = DtoTestInstanceFactory.typeVarianceDto()

    override val serializer: KSerializer<TypeVarianceDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<TypeVarianceDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, TypeVarianceDto>): TypeVarianceApi {
        TODO("Not yet implemented")
    }

}