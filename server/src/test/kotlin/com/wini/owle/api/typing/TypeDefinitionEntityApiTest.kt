package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeDefinition
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class TypeDefinitionEntityApiTest : BaseLongPkApiTest<TypeDefinition, TypeDefinitionApi>() {

    override val route: String = "/api/type-definition"

    override fun getDto(id: Long): TypeDefinition = DtoTestInstanceFactory.typeDefinitionDto(
        id = id
    )

    override val serializer: KSerializer<TypeDefinition> = TypeDefinition.serializer()
    
    override val listSerializer: KSerializer<List<TypeDefinition>> = ListSerializer(TypeDefinition.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TypeDefinition>,
        validator: BaseValidator<TypeDefinition>
    ): TypeDefinitionApi = TypeDefinitionApi(dll, validator)

}