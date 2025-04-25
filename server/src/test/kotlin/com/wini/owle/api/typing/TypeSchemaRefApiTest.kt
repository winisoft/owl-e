package com.wini.owle.api.typing

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.typing.TypeSchemaRef
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class TypeSchemaRefApiTest : BaseLongPkApiTest<TypeSchemaRef, TypeSchemaRefApi>() {

    override val route: String = "/api/type-schema-ref"

    override fun getDto(id: Long): TypeSchemaRef = DtoTestInstanceFactory.typeSchemaRefDto(
        id = id
    )

    override val serializer: KSerializer<TypeSchemaRef> = TypeSchemaRef.serializer()
    
    override val listSerializer: KSerializer<List<TypeSchemaRef>> = ListSerializer(TypeSchemaRef.serializer())

    override fun getApiInstance(
        dll: DLL<Long, TypeSchemaRef>,
        validator: BaseValidator<TypeSchemaRef>
    ): TypeSchemaRefApi = TypeSchemaRefApi(dll, validator)

}