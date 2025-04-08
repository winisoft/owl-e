package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeVarianceDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer


open class TypeVarianceApiTest : BaseLongPkApiTest<TypeVarianceDto, TypeVarianceApi>() {

    override val route: String = "/api/type-variance"

    override fun getDto(): TypeVarianceDto = DtoTestInstanceFactory.typeVarianceDto()

    override val serializer: KSerializer<TypeVarianceDto> = TypeVarianceDto.serializer()
    
    override val listSerializer: KSerializer<List<TypeVarianceDto>> = ListSerializer(TypeVarianceDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TypeVarianceDto>,
        validator: (TypeVarianceDto) -> Boolean
    ): TypeVarianceApi = TypeVarianceApi(dll, validator)

}