package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeDefinitionDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class TypeDefinitionApiTest : BaseLongPkApiTest<TypeDefinitionDto, TypeDefinitionApi>() {

    override val route: String = "/api/type-definition"

    override fun getDto(id: Long): TypeDefinitionDto = DtoTestInstanceFactory.typeDefinitionDto(
        id = id
    )

    override val serializer: KSerializer<TypeDefinitionDto> = TypeDefinitionDto.serializer()
    
    override val listSerializer: KSerializer<List<TypeDefinitionDto>> = ListSerializer(TypeDefinitionDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TypeDefinitionDto>,
        validator: BaseValidator<TypeDefinitionDto>
    ): TypeDefinitionApi = TypeDefinitionApi(dll, validator)

}