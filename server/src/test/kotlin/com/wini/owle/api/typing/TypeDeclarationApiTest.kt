package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeDeclarationDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class TypeDeclarationApiTest : BaseLongPkApiTest<TypeDeclarationDto, TypeDeclarationApi>() {

    override val route: String = "/api/type-declaration"

    override fun getDto(id: Long): TypeDeclarationDto = DtoTestInstanceFactory.typeDeclarationDto(
        id = id
    )

    override val serializer: KSerializer<TypeDeclarationDto> = TypeDeclarationDto.serializer()
    
    override val listSerializer: KSerializer<List<TypeDeclarationDto>> = ListSerializer(TypeDeclarationDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TypeDeclarationDto>,
        validator: DtoValidator<TypeDeclarationDto>
    ): TypeDeclarationApi = TypeDeclarationApi(dll, validator)

}