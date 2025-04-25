package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeParameter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class TypeParameterApiTest : BaseLongPkApiTest<TypeParameter, TypeParameterApi>() {

    override val route: String = "/api/type-parameter"

    override fun getDto(id: Long): TypeParameter = DtoTestInstanceFactory.typeParameterDto(
        id = id
    )

    override val serializer: KSerializer<TypeParameter> = TypeParameter.serializer()
    
    override val listSerializer: KSerializer<List<TypeParameter>> = ListSerializer(TypeParameter.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TypeParameter>,
        validator: BaseValidator<TypeParameter>
    ): TypeParameterApi = TypeParameterApi(dll, validator)

}