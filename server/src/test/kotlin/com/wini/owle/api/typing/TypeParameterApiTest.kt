package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeParameterDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer


open class TypeParameterApiTest : BaseLongPkApiTest<TypeParameterDto, TypeParameterApi>() {

    override val route: String = "/api/type-parameter"

    override fun getDto(): TypeParameterDto = DtoTestInstanceFactory.typeParameterDto()

    override val serializer: KSerializer<TypeParameterDto> = TypeParameterDto.serializer()
    
    override val listSerializer: KSerializer<List<TypeParameterDto>> = ListSerializer(TypeParameterDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TypeParameterDto>,
        validator: (TypeParameterDto) -> Boolean
    ): TypeParameterApi = TypeParameterApi(dll, validator)

}