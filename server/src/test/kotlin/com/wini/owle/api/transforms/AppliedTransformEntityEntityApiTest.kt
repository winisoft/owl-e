package com.wini.owle.api.transforms

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.transforms.AppliedTransform
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class AppliedTransformEntityEntityApiTest : BaseLongPkApiTest<AppliedTransform, AppliedTransformApi>() {

    override val route: String = "/api/applied-transform"

    override fun getDto(id: Long): AppliedTransform = DtoTestInstanceFactory.appliedTransformDto(
        id = id
    )

    override val serializer: KSerializer<AppliedTransform> = AppliedTransform.serializer()
    
    override val listSerializer: KSerializer<List<AppliedTransform>> = ListSerializer(AppliedTransform.serializer())

    override fun getApiInstance(
        dll: DLL<Long, AppliedTransform>,
        validator: BaseValidator<AppliedTransform>
    ): AppliedTransformApi = AppliedTransformApi(dll, validator)

}