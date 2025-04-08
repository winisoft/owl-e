package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeBoundsDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

open class TypeBoundsApiTest : BaseLongPkApiTest<TypeBoundsDto, TypeBoundsApi>() {

    override val route: String = "/api/type-bounds"

    override fun getDto(): TypeBoundsDto = DtoTestInstanceFactory.typeBoundDto()

    override val serializer: KSerializer<TypeBoundsDto> = TypeBoundsDto.serializer()
    
    override val listSerializer: KSerializer<List<TypeBoundsDto>> = ListSerializer(TypeBoundsDto.serializer())

    override fun getApiInstance(dll: DLL<Long, TypeBoundsDto>): TypeBoundsApi = TypeBoundsApi(dll)
}