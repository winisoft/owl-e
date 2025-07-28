package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeVariance
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class TypeVarianceApiTest : BaseLongPkApiTest<TypeVariance, TypeVarianceApi>() {

    override val route: String = "/api/type-variance"

    override fun getDto(id: Long): TypeVariance = DtoTestInstanceFactory.typeVarianceDto(
        id = id
    )

    override val serializer: KSerializer<TypeVariance> = TypeVariance.serializer()
    
    override val listSerializer: KSerializer<List<TypeVariance>> = ListSerializer(TypeVariance.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TypeVariance>,
        validator: DtoValidator<TypeVariance>
    ): TypeVarianceApi = TypeVarianceApi(dll, validator)

}