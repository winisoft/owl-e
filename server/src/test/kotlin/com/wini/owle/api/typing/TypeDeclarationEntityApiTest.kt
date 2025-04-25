package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeDeclaration
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class TypeDeclarationEntityApiTest : BaseLongPkApiTest<TypeDeclaration, TypeDeclarationApi>() {

    override val route: String = "/api/type-declaration"

    override fun getDto(id: Long): TypeDeclaration = DtoTestInstanceFactory.typeDeclarationDto(
        id = id
    )

    override val serializer: KSerializer<TypeDeclaration> = TypeDeclaration.serializer()
    
    override val listSerializer: KSerializer<List<TypeDeclaration>> = ListSerializer(TypeDeclaration.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TypeDeclaration>,
        validator: BaseValidator<TypeDeclaration>
    ): TypeDeclarationApi = TypeDeclarationApi(dll, validator)

}