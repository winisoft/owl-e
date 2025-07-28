package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeBound
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class TypeBoundsApiTestEntity : BaseLongPkApiTest<TypeBound, TypeBoundsApi>() {

    override val route: String = "/api/type-bounds"

    override fun getDto(id: Long): TypeBound = DtoTestInstanceFactory.typeBoundDto(
        id = id
    )

    override val serializer: KSerializer<TypeBound> = TypeBound.serializer()
    
    override val listSerializer: KSerializer<List<TypeBound>> = ListSerializer(TypeBound.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TypeBound>,
        validator: DtoValidator<TypeBound>
    ): TypeBoundsApi = TypeBoundsApi(dll, validator)
}