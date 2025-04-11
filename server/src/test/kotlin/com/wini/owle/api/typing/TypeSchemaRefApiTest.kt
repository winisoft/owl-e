package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeSchemaRefDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class TypeSchemaRefApiTest : BaseLongPkApiTest<TypeSchemaRefDto, TypeSchemaRefApi>() {

    override val route: String = "/api/type-schema-ref"

    override fun getDto(id: Long): TypeSchemaRefDto = DtoTestInstanceFactory.typeSchemaRefDto(
        id = id
    )

    override val serializer: KSerializer<TypeSchemaRefDto> = TypeSchemaRefDto.serializer()
    
    override val listSerializer: KSerializer<List<TypeSchemaRefDto>> = ListSerializer(TypeSchemaRefDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TypeSchemaRefDto>,
        validator: BaseValidator<TypeSchemaRefDto>
    ): TypeSchemaRefApi = TypeSchemaRefApi(dll, validator)

}