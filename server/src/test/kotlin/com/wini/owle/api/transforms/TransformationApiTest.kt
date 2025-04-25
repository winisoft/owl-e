package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.transforms.Transform
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class TransformationApiTest : BaseLongPkApiTest<Transform, TransformationApi>() {

    override val route: String = "/api/transformation"

    override fun getDto(id: Long): Transform = DtoTestInstanceFactory.transformationDto(
        id = id
    )

    override val serializer: KSerializer<Transform> = Transform.serializer()
    
    override val listSerializer: KSerializer<List<Transform>> = ListSerializer(Transform.serializer())

    override fun getApiInstance(
        dll: DLL<Long, Transform>,
        validator: BaseValidator<Transform>
    ): TransformationApi = TransformationApi(dll, validator)

}